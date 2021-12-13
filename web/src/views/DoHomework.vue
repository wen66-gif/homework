<template>
    <div>
        <!-- 顶部面包屑与作业名-->
        <div style="height: 50px;">
            <div style="margin: 10px 30px;float:left">
                <a-breadcrumb>
                    <a-breadcrumb-item>
                        <router-link :to="{
                                path:'/stu_course'
                            }">我学的课</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item>
                        <router-link :to="{
                                path:'/myHomework',
                                query:{
                                    courseId:this.courseId
                                }
                        }">我的作业</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item>
                        作业作答
                    </a-breadcrumb-item>
                </a-breadcrumb>
            </div>
            <div style="margin: 10px 30px;float: right">
                作业一
            </div>
        </div>
        <div id="main_content">
            <div v-for="(q,index) in questionList" :key="q.id">
                <template v-if="q.type===1 ">
                    <SingleCard :question="q" :studentAnswer="studentAnswer[index]"/>
                </template>
                <template v-if="q.type===2">
                    <MultiCard :question="q" :studentAnswer="studentAnswer[index]"/>
                </template>
                <template v-if="q.type===3">
                    <JudgeCard :question="q" :studentAnswer="studentAnswer[index]"/>
                </template>
                <template v-if="q.type===4">
                    <BlankCard :question="q" :studentAnswer="studentAnswer[index]"/>
                </template>
                <template v-if="q.type===5">
                    <ShortCard :question="q" :studentAnswer="studentAnswer[index]"/>
                </template>
                <a-divider :dashed="true"/>
            </div>
            <div style="text-align: center">
                <a-button size="large" type="primary" style="width: 100%;height: 50px" @click="submitHomework">提交</a-button>
            </div>

        </div>

    </div>
</template>

<script>
    import {mapMutations, mapState} from "vuex";
    import SingleCard from "../components/SingleCard";
    import MultiCard from "../components/MultiCard";
    import JudgeCard from "../components/JudgeCard";
    import BlankCard from "../components/BlankCard";
    import ShortCard from "../components/ShortCard";
    import axios from "axios"

    export default {
        name: "DoHomework",
        components: {ShortCard, BlankCard, JudgeCard, MultiCard, SingleCard},
        computed:{
            ...mapState(['courseId','studentAnswer'])
        },
        data(){
            return{
                questionList:[],
            }
        },
        mounted() {
            axios.get("/homework/getAllQuestion",{
                params:{
                    homeworkId:this.$route.query.homeworkId
                }
            }).then(res=>{
                if (res.data.code === "0"){
                    this.questionList = res.data.data
                    // 设置一些已知属性
                    this.questionList.forEach((q)=>{
                        if (q.type === 1 || q.type === 2)
                            this.ADD_STUDENTANSWER({homeworkId: q.homeworkId, choiceId: q.id})
                        else
                            this.ADD_STUDENTANSWER({homeworkId: q.homeworkId, unchoiceId: q.id})
                    })
                }
                else {
                    this.$message.error("获取作业详情失败")
                }
            })
            this.SET_COURSEID(this.$route.query.courseId)
        },
       methods:{
            ...mapMutations(['ADD_STUDENTANSWER','SET_COURSEID']),
           // 提交作业
           submitHomework(){
                //将下面这个学生答案传到student_answer
                console.log(this.studentAnswer)
               // 我前面是用失去焦点时保存输入的答案，最后一道题答完直接点提交，
               // 他要先去保存答案，再执行提交，如果保存的时候出错，可以试一下用延时函数，让他先保存完再提交
               // 延时函数如下
               // setTimeout(()=>{
               //     axios.post()
               // },100)
               // 或者直接调用
                // axios.post("")
               axios.post("homework/doHomeWork",this.studentAnswer).then(res=>{
                   console.log(res.data)
               })
           }
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