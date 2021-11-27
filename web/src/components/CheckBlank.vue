<template>
    <div >
        <div id="title" >
            <span style="white-space: pre">{{question.no}}.{{question.title}}</span>
            <span>（{{question.score}}分）</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span>正确答案：</span>
            <p v-for="(t,index) in question.trueAnswer">（{{index+1}}）{{t}}</p>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span style="float: left">学生答案：A</span>
            <div style="float: right">
                <span style="">评分：</span>
                <a-input-number :min="0" :max="question.score" v-model="score"></a-input-number>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CheckBlank",
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
            this.question.trueAnswer = this.parseAnswer(this.question.trueAnswer)
            this.answer.answer = this.parseAnswer(this.answer.answer)
        },
        methods:{
            parseAnswer(answer){
                return  answer.split("_")
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