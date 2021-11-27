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
            <span>正确答案：{{question.trueAnswer}}</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span style="float: left">学生答案：{{answer.answer}}</span>
            <div style="float: right">
                <span style="">评分：</span>
                <a-input-number :min="0" :max="question.score" v-model="score"></a-input-number>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CheckSingle",
        props:['question','answer'],
        data(){
            return{
                value: 1,
                score:this.answer.score | 0
            }
        },
        mounted() {
            this.question.trueAnswer = this.parseAnswer(this.question.trueAnswer)
            this.answer.answer = this.parseAnswer(this.answer.answer)
        },
        methods:{
            parseAnswer(answer){
                switch (answer) {
                    case "1":
                        answer = "A";
                        break;
                    case "2":
                        answer = "B";
                        break;
                    case "3":
                        answer = "C";
                        break;
                    case "4":
                        answer = "D";
                        break;
                }
                return answer
            }
        },
        watch:{
            score(){
                this.score = this.score == null ? 0 : this.score
                if (typeof this.score === "number" && this.score <= this.question.score)
                    this.$emit('updateScore',this.score)
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