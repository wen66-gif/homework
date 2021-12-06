<template>
    <div id="main">
        <div style="height: 50px">
            <!--面包屑-->
            <div style="margin: 10px 30px;float:left;">
                <a-breadcrumb>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/mycourse'
                        }">我教的课</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item> <router-link :to="{
                            path:'/course_manage',
                            query:{courseId:this.courseId,activeKey:2}
                        }">课程管理</router-link></a-breadcrumb-item>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/submit_list',
                            query: {homeworkId:this.homeworkId}
                        }">提交列表</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item>
                        批阅作业
                    </a-breadcrumb-item>
                </a-breadcrumb>
            </div>
            <div style="margin: 10px 30px;float: right">
                {{homeworkName}}
            </div>
        </div>
        <div id="main_content">
            <!--学生信息-->
            <!-- 加上v-if条件，让student.classes不为空时渲染，避免报错-->
            <div v-if="student.classes">
                <span >学号：{{student.no}}</span>
                <span>姓名：{{student.name}}</span>
                <span>班级：{{student.classes.name}}</span>
            </div>
            <a-divider :dashed="true" />
            <div v-for="(q,index) in questionList" :key="q.id">
                <template v-if="q.type==1 && studentAnswer[index]">
                    <CheckSingle :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==2 && studentAnswer[index]">
                    <CheckMulti :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==3 && studentAnswer[index]">
                    <CheckJudge :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==4 && studentAnswer[index]">
                    <CheckBlank :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==5 && studentAnswer[index]">
                    <CheckShort :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"  />
                </template>
                <a-divider :dashed="true"/>
            </div>
            <div>
                <span style="float:left;font-weight: bold;font-size: 18px">总得分：{{totalScore}}</span>
                <a-button type="primary" size="large" style="float:right;" @click="handleSubmit">提交</a-button>
            </div>
        </div>
    </div>
</template>

<script>
    import CheckSingle from "../components/CheckSingle";
    import CheckMulti from "../components/CheckMulti";
    import CheckJudge from "../components/CheckJudge";
    import CheckBlank from "../components/CheckBlank";
    import CheckShort from "../components/CheckShort";
    import {mapMutations, mapState} from "vuex";
    import axios from "axios";
    export default {
        name: "CheckHomework",
        components: {CheckShort, CheckBlank, CheckJudge, CheckMulti, CheckSingle},
        data(){
            return{
                // 题目标题，正确答案
                questionList:[],
                // 学生答案，得分
                studentAnswer:[],
                // 作业名称
                homeworkName:'',
                // 学生信息
                student:{}
            }
        },
        computed:{
          totalScore(){
              let total = 0
              this.studentAnswer.forEach((a)=>{
                  total += a.score
              })
              return total
          },
            ...mapState(['homeworkId','courseId'])
        },
        mounted() {
            // 获取作业所有题目
            axios.get("/homework/getAllQuestion",{
                params:{
                    homeworkId: this.$route.query.homeworkId
                }
            }).then(res=>{
                if (res.data.code==="0"){
                    this.questionList = res.data.data
                }
                else {
                    this.$message.error(res.data.msg)
                }
            })
            // 获取学生答案
            axios.get("/studentAnswer/getStudentAnswer",{
                params:{
                    studentNo:this.$route.query.studentNo,
                    homeworkId:this.$route.query.homeworkId
                }
            }).then(res=>{
                if (res.data.code==="0"){
                    this.studentAnswer = res.data.data
                }
                else {
                    this.$message.error(res.data.msg)
                }
            })
            // 获取作业名称
            axios.get("/homework/getHomeworkName",{
                params:{homeworkId:this.$route.query.homeworkId
                }
            }).then(res=>{
                this.homeworkName = res.data.data
            })
            // 获取作答学生信息
            axios.get("/student/getStudent",{
                params:{studentNo:this.$route.query.studentNo}
            }).then(res=>{
                this.student = res.data.data
            })
            this.SET_HOMEWORKID(this.$route.query.homeworkId)
        },
        methods:{
            ...mapMutations(['SET_HOMEWORKID']),
            handleSubmit(){
                // 提交批阅结果
                console.log("提交",this.studentAnswer)
                let data = JSON.stringify({answer:this.studentAnswer,score:this.totalScore,homeworkId:this.$route.query.homeworkId})
                axios.put("/homework/checkHomework",data,{headers: {
                        'Content-Type': 'application/json'
                    }}).then(res=>{
                        if (res.data.code==="0"){
                            this.$message.success("提交成功");
                            this.$router.push({
                                path:'/submit_list',
                                query:{
                                    homeworkId:this.$route.query.homeworkId
                                }
                            })
                        }
                        else {
                            this.$message.error(res.data.msg)
                        }
                })
            },
            updateScore(data,index){
                console.log("接收到",data)
                console.log("下标：",index)
                this.studentAnswer[index].score = data
                console.log("修改后的分数",this.studentAnswer[index].score)
            },

        }
    }
</script>

<style scoped>
    #main_content {
        margin: 0 30px 30px;
        background-color: white;
        padding: 20px;
        overflow: auto;
        zoom: 1
    }
    #main_content span {
        margin: 0 5px;
        color: black;
    }
</style>