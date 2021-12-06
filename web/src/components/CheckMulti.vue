<template>
    <div >
        <div id="title" >
            <span >{{question.no}}.{{question.title}}</span>
            <span>（{{question.score}}分）</span>
        </div>
        <div style="margin-top: 5px">
            <p >A.{{question.A}}</p>
            <p >B.{{question.B}}</p>
            <p >C.{{question.C}}</p>
            <p >D.{{question.D}}</p>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span>正确答案：</span>
            <span v-for="t in question.trueAnswer">{{t+" "}}</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span style="float: left">学生答案：</span>
            <span v-for="a in answer.answer">{{a+" "}}</span>
            <div style="float: right">
                <span >评分：</span>
                <a-input-number :min="0" :max="question.score" v-model="score"></a-input-number>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CheckMulti",
        props:['question','answer'],
        data(){
            return{
                score:this.answer.score | 0
            }
        },
        watch:{
            // 观察分数
            score(){
                // 如果是空即设为0，否则是他本身
                this.score = this.score == null ? 0 : this.score
                // 类型是数字，评分分数小于等于题目分数-->合法的分数
                if (typeof this.score === "number" && this.score <= this.question.score)
                    this.$emit('updateScore',this.score)
            }
        },
        methods:{
          parseAnswer(answer){
              answer = answer.split("_")
              answer = answer.map(t => {
                  return t === "1" ? "A" : t==="2" ? "B" : t==="3" ? "C" : "D";
              });
              return answer
          }
        },
        mounted() {
            this.question.trueAnswer = this.parseAnswer(this.question.trueAnswer)
            this.answer.answer = this.parseAnswer(this.answer.answer)
        }
    }
</script>

<style scoped>
    #title span {
        color: black;
        font-size: 15px;
    }
    div {
        overflow: auto;
        zoom: 1;
        color: black;
    }
</style>