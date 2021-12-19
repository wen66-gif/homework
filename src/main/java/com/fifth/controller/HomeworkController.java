package com.fifth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.common.Result;
import com.fifth.domain.*;
import com.fifth.mapper.*;
import com.fifth.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/homework")
@CrossOrigin
public class HomeworkController {

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private ChoiceMapper choiceMapper;

    @Autowired
    private UnchoiceMapper unchoiceMapper;

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;

    @Autowired
    private AnswerSummaryMapper answerSummaryMapper;

    /**
     * 做作业了
     * @param list
     * @return
     */
    @PostMapping("/doHomeWork/{id}")
    public Result doHomework(@RequestBody List<StudentAnswer> list,
                             @PathVariable int id) {
        //判断是否已作答
        boolean isAnswer = studentAnswerMapper.selectList(new QueryWrapper<StudentAnswer>().eq("student_no",CurrentUser.getCurrentUserId())).size()<=0 ? true : false;
        //2 进行基础评分 插入数据返回处理结果
        AtomicReference<Float> totalScore = new AtomicReference<>((float) 0);
        AtomicReference<Float> score = new AtomicReference<>(0f);
        list.stream().forEach(studentAnswer -> {
            if (StringUtils.isEmpty(studentAnswer.getChoiceId())) {
                Unchoice unchoice = unchoiceMapper.selectById(studentAnswer.getUnchoiceId());
                if (studentAnswer.getAnswer().equalsIgnoreCase(unchoice.getTrueAnswer())) score.set((score.get()+unchoice.getScore()));
            } else {
                Choice choice = choiceMapper.selectById(studentAnswer.getChoiceId());
                if (studentAnswer.getAnswer().equalsIgnoreCase(choice.getTrueAnswer())) score.set((score.get() + choice.getScore()));
            }
            studentAnswer.setStudentNo(CurrentUser.getCurrentUserId());
            studentAnswer.setScore(score.get());
            if (isAnswer) studentAnswerMapper.insert(studentAnswer);
            else {
                if (StringUtils.isEmpty(studentAnswer.getChoiceId())) {
                    studentAnswerMapper.update(studentAnswer,
                            new UpdateWrapper<StudentAnswer>()
                                    .eq("homework_id",studentAnswer.getHomeworkId())
                                    .eq("student_no", CurrentUser.getCurrentUserId())
                                    .eq("unchoice_id", studentAnswer.getUnchoiceId()));
                } else {
                    studentAnswerMapper.update(studentAnswer,
                            new UpdateWrapper<StudentAnswer>()
                                    .eq("homework_id",studentAnswer.getHomeworkId())
                                    .eq("student_no", CurrentUser.getCurrentUserId())
                                    .eq("choice_id", studentAnswer.getChoiceId()));

                }

            }
            totalScore.updateAndGet(v -> new Float((float) (v + score.get())));
            score.set(0f);
        });
        if (isAnswer) answerSummaryMapper.insert(new AnswerSummary(CurrentUser.getCurrentUserId(),id,new Date(),Float.parseFloat(totalScore.toString())));
        else {
            AnswerSummary answerSummary = new AnswerSummary(CurrentUser.getCurrentUserId(),id,new Date(),Float.parseFloat(totalScore.toString()));
            answerSummaryMapper.update(answerSummary, new UpdateWrapper<AnswerSummary>().eq("student_no",CurrentUser.getCurrentUserId()).eq("homework_id", list.get(0).getHomeworkId()));
        }
        return Result.success();
    }

    /**
     * 查询 所学 课程HomeWork
     *
     * @param courseId 课程号
     * @return
     */
    @GetMapping("/myHomework")
    public Result selectWorkList(@RequestParam Integer courseId,
                                 @RequestParam int pageNum,
                                 @RequestParam int pageSize,
                                 @RequestParam(required = false) String sortField,
                                 @RequestParam(required = false) String sortOrder) {
        Page<Homework> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("publishTime");
        if (sortOrder!=null){
            if (sortOrder.equals("ascend")){
                orderItem.setAsc(true);
            }
            else {
                orderItem.setAsc(false);
            }
        }
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        IPage<Homework> homeworkIPage = homeworkMapper.selectHomeWork(page, CurrentUser.getCurrentUserId(), courseId);
        return Result.success(homeworkIPage);
    }

    // 查询一个课程已发布的所有作业
    @GetMapping("/allPublishHomework")
    public Result allPublishHomework(@RequestParam int courseId){
        List<Homework> homeworkList = homeworkMapper.allPublishHomework(courseId);
        if (homeworkList != null){
            return Result.success(homeworkList);
        }
        else {
            return Result.error("-1","查询失败");
        }
    }

    // 查询一个课程的指定作业
    @GetMapping("/oneHomework")
    public Result oneHomework(@RequestParam int homeworkId){
        Homework homework = homeworkMapper.oneHomework(homeworkId);
        if (homework != null){
            return Result.success(homework);
        }
        else {
            return Result.error("-1","查询失败");
        }
    }

    // 查询一个课程的所有作业
    @GetMapping("/allHomework")
    public Result allHomework(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam int courseId,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder){
        Page<Homework> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = new OrderItem();
        if (sortField!=null){
            if (sortField.equals("createTime"))
                orderItem.setColumn("create_time");
            else {
                orderItem.setColumn("update_time");
            }
        }
        if (sortOrder!=null){
            if (sortOrder.equals("ascend")){
                orderItem.setAsc(true);
            }
            else {
                orderItem.setAsc(false);
            }
        }
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        Page<Homework> homeworkPage = homeworkMapper.selectPage(page, new QueryWrapper<Homework>().eq("course_id", courseId));
        return Result.success(homeworkPage);
    }

    // 添加一个新作业
    @PostMapping("/addHomework")
    public Result addHomework(
            @RequestBody JSONObject object){
        try {
            // 提取jsonObject中的内容
            // 将unchoice数组提取出来
            JSONArray unchoiceArray= object.getJSONArray("unchoice");
            // 将jsonArray转为List<实体类>
            List<Unchoice> unchoiceList = unchoiceArray.toJavaList(Unchoice.class);
            // 将choice数组提取出来
            JSONArray choiceArray= object.getJSONArray("choice");
            // 将jsonArray转为List<实体类>
            List<Choice> choiceList = choiceArray.toJavaList(Choice.class);
            // 提取分数字段
            float score =Float.parseFloat(object.get("score").toString());
            // 提取作业名字段
            String name = (String)object.get("name");
            // 提取课程id
            int courseId = Integer.parseInt(object.get("courseId").toString());
            // 封装作业信息
            Date currentTime = new Date();
            Homework homework = new Homework(name,score,currentTime,courseId);
            if (homeworkMapper.insert(homework) > 0){
                // 数据库设计时，给作业名与课程id做了唯一约束，可以根据这两个字段查询
                Homework h = homeworkMapper.selectOne(new QueryWrapper<Homework>()
                        .eq("name",name).eq("course_id",courseId));
                for (int i = 0; i < choiceList.size(); i++) {
                    // 插入选择题
                    System.out.println(choiceList.get(i));
                    choiceList.get(i).setHomeworkId(h.getId());
                    choiceMapper.insert(choiceList.get(i));
                }
                for (int i = 0; i < unchoiceList.size(); i++) {
                    // 插入非选择题
                    unchoiceList.get(i).setHomeworkId(h.getId());
                    unchoiceMapper.insert(unchoiceList.get(i));
                }
                return Result.success();
            }
            else {
                return Result.error("-1","新建作业失败");
            }
        }catch (Exception e){
            return Result.error("-2","作业名称重复！");
        }

    }

    // 更新一个作业
    @PutMapping("/updateHomework")
    public Result updateHomework(
            @RequestBody JSONObject object){
        try {
            // 提取jsonObject中的内容
            // 将unchoice数组提取出来
            JSONArray unchoiceArray= object.getJSONArray("unchoice");
            // 将jsonArray转为List<实体类>
            List<Unchoice> unchoiceList = unchoiceArray.toJavaList(Unchoice.class);
            // 将choice数组提取出来
            JSONArray choiceArray= object.getJSONArray("choice");
            // 将jsonArray转为List<实体类>
            List<Choice> choiceList = choiceArray.toJavaList(Choice.class);
            // 提取分数字段
            float score =Float.parseFloat(object.get("score").toString());
            // 提取作业名字段
            String name = (String)object.get("name");
            // 提取作业id
            int homeworkId = Integer.parseInt(object.get("homeworkId").toString());
            // 封装作业信息
            Date updateTime = new Date();
            Homework homework = new Homework(homeworkId,name,score,updateTime);
            // 更新作业表信息
            if (homeworkMapper.updateById(homework) > 0){
                // 更新作业，会涉及到删除，新增，更新题目，为了方便，统一更新时清空相关题目，再将更新后的题目插入
                // 题目清空
                choiceMapper.delete(new QueryWrapper<Choice>().eq("homework_id",homeworkId));
                for (int i = 0; i < choiceList.size(); i++) {
                    choiceList.get(i).setHomeworkId(homeworkId);
                    choiceMapper.insert(choiceList.get(i));
                }
                unchoiceMapper.delete(new QueryWrapper<Unchoice>().eq("homework_id",homeworkId));
                for (int i = 0; i < unchoiceList.size(); i++) {
                    unchoiceList.get(i).setHomeworkId(homeworkId);
                    unchoiceMapper.insert(unchoiceList.get(i));
                }
                return Result.success();
            }
            else {
                return Result.error("-1","更新作业失败");
            }
        }catch (Exception e){
            return Result.error("-2","作业名称重复！");
        }

    }

    // 从作业库删除作业，所有相关记录都删除
    @DeleteMapping("/deleteHomework")
    public Result deleteHomework(@RequestParam int homeworkId){
        try {
            // 从作业表删除该作业，外键为该id的记录都删除
            homeworkMapper.delete(new QueryWrapper<Homework>().eq("id",homeworkId));
            return Result.success();
        }catch (Exception e){
            return Result.error("-1","删除失败");
        }
    }

    // 获取作业所有题目
    @GetMapping("/getAllQuestion")
    public Result getAllQuestion(@RequestParam int homeworkId){
        try {
            List<Choice> choiceList = choiceMapper.selectList(new QueryWrapper<Choice>().eq("homework_id", homeworkId));
            List<Unchoice> unchoiceList = unchoiceMapper.selectList(new QueryWrapper<Unchoice>().eq("homework_id", homeworkId));
            int i = 0, j = 0;
            List<Question> quetionList = new ArrayList<>();
            // 整合选择题、非选择题，按题号排序
            for (int k = 0; k < choiceList.size()+unchoiceList.size(); k++) {
                // choiceList排完，将剩下的非选择全部放入
                if (i>=choiceList.size()){
                    Unchoice unchoice = unchoiceList.get(j);
                    Question q = new Question(unchoice.getId(),unchoice.getNo(),unchoice.getTitle(),unchoice.getTrueAnswer(),
                            unchoice.getScore(),unchoice.getType(),unchoice.getHomeworkId());
                    quetionList.add(q);
                    j++;
                }
                // unchoiceList排完，将剩下的选择题全部放入
                else if (j>=unchoiceList.size()){
                    Choice choice = choiceList.get(i);
                    Question q = new Question(choice.getId(),choice.getNo(),choice.getTitle(),choice.getA(),choice.getB(),
                            choice.getC(),choice.getD(),choice.getTrueAnswer(),choice.getScore(),choice.getType(),choice.getHomeworkId());
                    quetionList.add(q);
                    i++;
                }
                else {
                    if (choiceList.get(i).getNo()<unchoiceList.get(j).getNo()){
                        Choice choice = choiceList.get(i);
                        Question q = new Question(choice.getId(),choice.getNo(),choice.getTitle(),choice.getA(),choice.getB(),
                                choice.getC(),choice.getD(),choice.getTrueAnswer(),choice.getScore(),choice.getType(),choice.getHomeworkId());
                        quetionList.add(q);
                        i++;
                    }
                    else {
                        Unchoice unchoice = unchoiceList.get(j);
                        Question q = new Question(unchoice.getId(),unchoice.getNo(),unchoice.getTitle(),unchoice.getTrueAnswer(),
                                unchoice.getScore(),unchoice.getType(),unchoice.getHomeworkId());
                        quetionList.add(q);
                        j++;
                    }
                }
            }
            return Result.success(quetionList);
        }catch (Exception e){
            return Result.error("-1","查询失败");
        }
    }

    // 评改作业
    @PutMapping("/checkHomework")
    public Result checkHomework(@RequestBody JSONObject jsonObject){
        try {
            // 获取分数
            JSONArray jsonArray = jsonObject.getJSONArray("answer");
            List<StudentAnswer> studentAnswerList = jsonArray.toJavaList(StudentAnswer.class);
            // 获取作业id
            int homeworkId = Integer.parseInt(jsonObject.get("homeworkId").toString());
            // 获取学生总得分
            float score = Float.parseFloat(jsonObject.get("score").toString());
            for (StudentAnswer studentAnswer : studentAnswerList) {
                // 更新分数
                StudentAnswer s = new StudentAnswer();
                s.setScore(studentAnswer.getScore());
                studentAnswerMapper.update(s,new QueryWrapper<StudentAnswer>().eq("id",studentAnswer.getId()));
            }
            // 更新学生作答汇总表的总分数
            AnswerSummary answerSummary = new AnswerSummary();
            answerSummary.setScore(score);
            answerSummaryMapper.update(answerSummary,new QueryWrapper<AnswerSummary>().eq("homework_id",homeworkId));
            return Result.success();
        }catch (Exception e){
            return Result.error("-1","提交失败");
        }
    }

    /***
     * 根据ID查询Homework数据
     * @param homeworkId
     * @return
     */
    @GetMapping("/getHomeworkName")
    public Result findById(@RequestParam Integer homeworkId) {
        //调用homeworkMapper实现根据主键查询Homework
        Homework homework = homeworkMapper.selectById(homeworkId);
        return Result.success(homework.getName());
    }

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用homeworkMapper实现根据主键删除
        if (homeworkMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Homework数据
     * @param homework
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Homework homework, @PathVariable Integer id) {
        //设置主键值
        homework.setId(id);
        //调用homeworkMapper实现修改Homework
        if (homeworkMapper.update(homework, new UpdateWrapper<Homework>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Homework数据
     * @param homework
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Homework homework) {
        //调用homeworkMapper实现添加Homework
        if (homeworkMapper.insert(homework) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }



    /***
     * 查询Homework全部数据
     * @return
     */
    @GetMapping
    public Result<List<Homework>> findAll() {
        //调用homeworkMapper实现查询所有Homework
        List<Homework> list = homeworkMapper.selectList(null);
        return Result.success(list);
    }
}
