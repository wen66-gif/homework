<template>
    <!--用一个数组包含所有的题目，方便编辑时的删除
        例如，每次添加一道题目，不论什么类型，先存到这个数组里面
        点击右侧删除时，根据index删除题目，重新排序题目，
        用这个数组的好处就是排序简单，直接用index+1作为题号
        在编辑完成后，将这个数组分成选择题和非选择题两个数组，
        主要用type类型分组，type=1选择题   type=2非选择题
        遍历整个题目数组，判断type，放进不同数组，将这两个数组传给后端

        如果一开始用两个数组存题目，选择和非选择各放进不同数组，
        在传给后端时不用分组直接传，简单。但是如果编辑过程有删除题目操作，
        需要同时遍历两个数组，看哪个元素的题号是对应要删除的。
        并且在对题目排序时，也要比对两个数组的题号一个一个拿，比较麻烦

        题目排序的设计是：按先后顺序，先编辑的题目题号在前，不涉及调整题目顺序，
        这个设计看起来不太好，但是目前我不想做太多-->
    <div>
        <!--作业名位置-->
        <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #E1E3E6; display: flex;background-color: white">

            <div style="width: 400px; padding-left: 30px; font-weight: bold; color: black" >标题：
                <a-input placeholder="未命名作业" v-model="homeworkName" style="width: 150px;border: none"/>
                <span style="color: red;font-size: 12px" v-if="display">请输入作业名称！</span>
            </div>
            <div style="flex: 1"></div>
            <div style="width: 200px; ">
                <a-button @click="back">返回</a-button>
                <a-button type="primary" style="margin-left: 30px" @click="saveHomework">保存</a-button>
            </div>
        </div>
        <!--面包屑-->
        <div style="margin: 10px 30px">
            <a-breadcrumb>
                <a-breadcrumb-item><router-link :to="{
                        path:'/mycourse'
                    }">我教的课</router-link></a-breadcrumb-item>
                <a-breadcrumb-item><router-link :to="{
                    path:'/course_manage',
                    query:{courseId:this.courseId,activeKey:2}
                }">课程管理</router-link></a-breadcrumb-item>
                <a-breadcrumb-item>编辑作业</a-breadcrumb-item>
            </a-breadcrumb>

        </div>
        <div>
            <!--题目列表-->
            <div id="questionList">
                <!--顶部统计-->
                <div>
                    <span style="float: left">
                        题量：{{questionList.length}}
                    </span>
                    <span style="float: right">
                        总分：{{totalScore}}
                    </span>
                </div>
                <!--题目-->
                <div style="margin-top: 30px;height:530px;overflow:auto">
                    <a-list item-layout="horizontal" :data-source="questionList">
                        <a-list-item slot="renderItem" slot-scope="item, index" >
                            <a-list-item-meta @click="detail(index)">
                                <span slot="title">{{index+1}}.{{item.title }}</span>
                            </a-list-item-meta>
                            <a-icon type="close" slot="actions" @click="DEL_QUESTION(index)"/>
                        </a-list-item>
                    </a-list>
                </div>
            </div>
            <!--题目编辑面板-->
            <div id="editPanel" style="overflow: auto">
                <div>
                    <a-tabs v-model="activeKey"  @change="callback" @tabClick="clearForm">
                        <a-tab-pane :key="1" tab="单选题" :forceRender="true">
                            <SingleChoice ref="single" />
                        </a-tab-pane>
                        <a-tab-pane :key="2" tab="多选题" :forceRender="true">
                            <MultiChoice ref="multi" />
                        </a-tab-pane>
                        <a-tab-pane :key="3" tab="判断题" :forceRender="true">
                            <Judgment ref="judge" />
                        </a-tab-pane>
                        <a-tab-pane :key="4" tab="填空题" :forceRender="true">
                            <FillBlank ref="blank" />
                        </a-tab-pane>
                        <a-tab-pane :key="5" tab="简答题" :forceRender="true">
                            <ShortAnswer ref="short" />
                        </a-tab-pane>
                    </a-tabs>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapState} from "vuex";
    import SingleChoice from "../components/SingleChoice";
    import MultiChoice from "../components/MultiChoice";
    import Judgment from "../components/Judgment";
    import FillBlank from "../components/FillBlank";
    import ShortAnswer from "../components/ShortAnswer";
    import {nanoid} from "nanoid";
    import axios from 'axios';
    import qs from 'qs'
    export default {
        name: "EditHomework",
        components: {ShortAnswer, FillBlank, Judgment, MultiChoice, SingleChoice},
        data(){
            return{
                display:false,
                activeKey:1,
                homeworkName:"",
            }
        },
        computed:{
            ...mapGetters(['totalScore']),
            ...mapState(['questionList','choiceType','unChoiceType','courseId']),

        },
        mounted() {
            // id不为空，获取作业所有题目
          if (this.$route.query.homeworkId != null){
              axios.get("/homework/getAllQuestion",{
                  params:{
                      homeworkId: this.$route.query.homeworkId
                  }
              }).then(res=>{
                  this.LOAD_QUESTION(res.data.data)
              })
              // 获取作业名称
              axios.get("/homework/getHomeworkName",{
                  params:{homeworkId:this.$route.query.homeworkId
                  }
              }).then(res=>{
                  this.homeworkName = res.data.data
              })
              this.SET_HOMEWORKID(this.$route.query.homeworkId)
          }
          else {
              this.CLEARALLQUESTION()
          }
          this.SET_COURSEID(this.$route.query.courseId)

        },
        methods:{
            // 清除表单内容,这个功能主要是解决点击左侧题目修改题目时，
            // 不进行修改，题目内容仍然显示在表单上
            clearForm(){
                if (this.$refs.single){
                    this.$refs.single.form.resetFields()
                    this.$refs.single.A = ''
                    this.$refs.single.B = ''
                    this.$refs.single.C = ''
                    this.$refs.single.D = ''
                    this.$refs.single.id = ''
                }
                if (this.$refs.multi){
                    this.$refs.multi.form.resetFields()
                    this.$refs.multi.A = ''
                    this.$refs.multi.B = ''
                    this.$refs.multi.C = ''
                    this.$refs.multi.D = ''
                    this.$refs.multi.id = ''
                }
                if (this.$refs.judge){
                    this.$refs.judge.form.resetFields()
                    this.$refs.judge.blankId = [{ id:nanoid()}]
                    this.$refs.judge.id = ''
                }
                if (this.$refs.blank){
                    this.$refs.blank.form.resetFields()
                    this.$refs.blank.blankId = [{id:nanoid()}]
                    this.$refs.blank.id = ''
                }
                if (this.$refs.short){
                    this.$refs.short.form.resetFields()
                    this.$refs.short.id = ''
                }
            },
            detail(index){
                var detail = this.questionList[index]
                switch (detail.type) {
                    case 1:
                        this.activeKey=1
                        this.$refs.single.form.setFieldsValue({"score":detail.score,"title":detail.title,"trueAnswer":detail.trueAnswer})
                        this.$refs.single.A = detail.A
                        this.$refs.single.B = detail.B
                        this.$refs.single.C = detail.C
                        this.$refs.single.D = detail.D
                        this.$refs.single.id = detail.id
                        break
                    case 2:
                        this.activeKey=2
                        var choiceArr = detail.trueAnswer.split("_")
                                    this.$refs.multi.form.setFieldsValue({"score":detail.score,"title":detail.title,"trueAnswer":choiceArr})
                                    this.$refs.multi.A = detail.A
                                    this.$refs.multi.B = detail.B
                                    this.$refs.multi.C = detail.C
                                    this.$refs.multi.D = detail.D
                                    this.$refs.multi.id = detail.id
                        break
                    case 3:
                        this.activeKey=3
                        this.$refs.judge.form.setFieldsValue({"score":detail.score,"title":detail.title,"trueAnswer":detail.trueAnswer})
                        this.$refs.judge.id = detail.id
                        break
                    case 4:
                        this.activeKey=4
                        // 每个空的内容
                        var blankArr = detail.trueAnswer.split("_")
                        // 每个空的id,用于标识表单条目
                        var idArr = []
                        // 根据题目数量生成多个id
                        for(let i=0;i<blankArr.length;i++){
                            var id = nanoid()
                            idArr.push({id})
                        }
                        // 初始表单值
                        this.$refs.blank.form.setFieldsValue({"score":detail.score,"title":detail.title})
                        this.$refs.blank.blankId = idArr
                        this.$refs.blank.blankContent = blankArr
                        this.$refs.blank.id = detail.id
                        break
                    case 5:
                        this.activeKey=5
                        this.$refs.short.form.setFieldsValue({"score":detail.score,"title":detail.title,"trueAnswer":detail.trueAnswer})
                        this.$refs.short.id = detail.id
                        break
                }
            },
            saveHomework(){
                // 没有输入作业名
                if (this.homeworkName.length == 0){
                    this.display = true
                }
                else {
                    this.display = false
                    this.questionList.forEach((q,i)=>{
                        // 添加题号属性
                        q.no = i+1
                        // 删除id属性
                        delete q.id

                        // 将选择题，非选择题分类
                        if (q.type==1 || q.type==2){
                            this.ADD_CHOICETYPE(q)
                        }
                        else {
                            this.ADD_UNCHOICETYPE(q)
                        }
                    })

                    // console.log("选择题：",this.choiceType)
                    // console.log("非选择题：",this.unChoiceType)
                    // let data = qs.stringify({name:this.homeworkName,score:this.totalScore,choice:this.choiceType,unchoice:this.unChoiceType},{indices:false})
                    // console.log(data)
                    // let data = JSON.stringify({courseId:this.$route.query.courseId,name:this.homeworkName,score:this.totalScore,choice:this.choiceType,unchoice:this.unChoiceType})
                    // console.log(d)
                    // id不为空，进行更新作业
                    if (this.$route.query.homeworkId != null) {
                        let data = JSON.stringify({homeworkId:this.$route.query.homeworkId,name:this.homeworkName,score:this.totalScore,choice:this.choiceType,unchoice:this.unChoiceType})
                        console.log(data)
                        axios.put("/homework/updateHomework",data,{headers: {
                                'Content-Type': 'application/json'
                            }}).then(res=>{
                            if (res.data.code==="0"){
                                // 清除vuex保存的题目
                                this.CLEARALLQUESTION()
                                this.$router.push({
                                    path:'/homeworkrep',
                                    query:{
                                        courseId:this.$route.query.courseId
                                    }
                                })
                                this.$message.success("更新作业成功")
                            }
                            else if (res.data.code==="-2"){
                                this.$message.error(res.data.msg)
                            }
                        })
                    }
                    else {
                        // id为空，新增作业
                        let data = JSON.stringify({courseId:this.$route.query.courseId,name:this.homeworkName,score:this.totalScore,choice:this.choiceType,unchoice:this.unChoiceType})
                        axios.post("/homework/addHomework",data,{headers: {
                                'Content-Type': 'application/json'
                            }}).then(res=>{
                            if (res.data.code==="0"){
                                this.CLEARALLQUESTION()
                                this.$router.push({
                                    path:'/homeworkrep',
                                    query:{
                                        courseId:this.$route.query.courseId
                                    }
                                })
                                this.$message.success("添加作业成功")
                            }
                            else if (res.data.code==="-2"){
                                this.$message.error(res.data.msg)
                            }
                        })
                    }
                }

            },
            callback(){

            },
            // 返回上一页
            back(){
                this.$router.go(-1);//返回上一层
            },
            ...mapMutations(['LOAD_QUESTION','DEL_QUESTION','ADD_CHOICETYPE','ADD_UNCHOICETYPE','CLEARALLQUESTION','SET_COURSEID','SET_HOMEWORKID']),

        }
    }
</script>

<style scoped>
    #questionList {
        padding: 10px;
        top: 150px;
        width: 250px;
        height: 600px;
        background-color: white;
        float: left;

    }
    #editPanel {
        padding: 10px;
        margin-left: 50px;
        top: 150px;
        width: 1200px;
        height: 600px;
        background-color: white;
        float: left;
    }
</style>