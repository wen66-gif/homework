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
                            path:'/course_manage'
                        }">课程管理</router-link></a-breadcrumb-item>
                    <a-breadcrumb-item>
                        <router-link :to="{
                            path:'/submit_list'
                        }">提交列表</router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item>
                        批阅作业
                    </a-breadcrumb-item>
                </a-breadcrumb>
            </div>
            <div style="margin: 10px 30px;float: right">
                作业一
            </div>
        </div>
        <div id="main_content">
            <!--学生信息-->
            <div>
                <span >学号：20191001</span>
                <span>姓名：张三</span>
                <span>班级：软工1903</span>
            </div>
            <a-divider :dashed="true" />
            <div v-for="(q,index) in questionList" :key="q.id">
                <template v-if="q.type==1">
                    <CheckSingle :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==2">
                    <CheckMulti :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==3">
                    <CheckJudge :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else-if="q.type==4">
                    <CheckBlank :question="q" :answer="studentAnswer[index]" @updateScore="updateScore($event,index)"/>
                </template>
                <template v-else>
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
    export default {
        name: "CheckHomework",
        components: {CheckShort, CheckBlank, CheckJudge, CheckMulti, CheckSingle},
        data(){
            return{
                // 题目标题，正确答案
                questionList:[
                    {
                        id:"001",
                        // 题号
                        no:"1",
                        title:"为什么地球围着地球转？",
                        // 类型
                        type:1,
                        A:"aaa",
                        B:"bbb",
                        C:"ccc",
                        D:"ddd",
                        trueAnswer:"1",
                        score:5,
                    },
                    {
                        id:"002",
                        // 题号
                        no:"2",
                        title:"为什么地球围着地球转？",
                        // 类型
                        type:2,
                        A:"aaa",
                        B:"bbb",
                        C:"ccc",
                        D:"ddd",
                        trueAnswer:"1_2",
                        score:5,
                    },
                    {
                        id:"003",
                        // 题号
                        no:"3",
                        title:"这道题对吗？",
                        // 类型，判断
                        type:3,
                        trueAnswer:"1",
                        score:5,
                    },
                    {
                        id:"004",
                        // 题号
                        no:"4",
                        title:
                            "第一空__________,第二空_____________",
                        // 类型
                        type:4,
                        trueAnswer:"床前明月光_疑是地上霜",
                        score:5,
                    },
                    {
                        id:"005",
                        // 题号
                        no:"5",
                        title:"为什么地球围着地球转？",
                        // 类型
                        type:5,
                        trueAnswer:"1dfvdfvdfvdfvdfvdfbgn广东分公司答复不是",
                        score:5,
                    }
                ],
                // 学生答案，得分
                studentAnswer:[
                    {
                        id:"1",
                        answer:"1",
                        score:5
                    },
                    {
                        id:"2",
                        answer:"1",
                        score:0
                    },
                    {
                        id:"3",
                        answer:"1",
                        score:5
                    },
                    {
                        id:"4",
                        answer:"床前明月光_疑似地上霜",
                        score:0
                    },
                    {
                        id:"5",
                        answer:"标识符是生动的是ad擦所大错多若若若若1",
                        score:''
                    },
                ],

            }
        },
        computed:{
          totalScore(){
              let total = 0
              this.studentAnswer.forEach((a)=>{
                  total += a.score
              })
              return total
          }

        },
        methods:{
            handleSubmit(){
                // 提交批阅结果
                console.log("提交",this.studentAnswer)
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