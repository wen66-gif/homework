<template>
    <div >
        <div id="title" >
            <span >{{question.no}}.{{question.title}}</span>
            <span>（{{question.score}}分）</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span>正确答案：{{question.trueAnswer}}</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <div style="float: left;">
                <span >学生答案：{{answer.answer}}</span>
            </div>
            <div style="float: right">
                <span style="">评分：</span>
                <a-input-number :min="0" :max="question.score" v-model="score"></a-input-number>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CheckJudge",
        props:['question','answer'],
        data(){
            return{
                score:this.answer.score | 0
            }
        },
        watch:{
            score(){
                this.score = this.score == null ? 0 : this.score
                if (typeof this.score === "number" && this.score <= this.question.score)
                    this.$emit('updateScore',this.score)
            }
        },
        mounted() {
            this.answer.answer = this.parseAnswer(this.answer.answer)
            this.question.trueAnswer = this.parseAnswer(this.question.trueAnswer)
        },
        methods:{
            parseAnswer(answer){
                switch (answer) {
                    case "1":
                        answer = "正确";
                        break;
                    case "2":
                        answer = "错误";
                        break;
                }
                return answer
            }
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