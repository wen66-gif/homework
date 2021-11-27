<template>
    <div >
        <div id="title" >
            <span style="white-space: pre">{{question.no}}.{{question.title}}</span>
            <span>（{{question.score}}分）</span>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span>参考答案：</span>
            <p >{{question.trueAnswer}}</p>
        </div>
        <hr color="#e6e6e6" size="1"/>
        <div>
            <span style="float: left">学生答案：</span>
            <p>
                {{answer.answer}}
            </p>
            <div style="float: right">
                <span style="">评分：</span>
                <a-input-number :min="0" :max="question.score" v-model="score"></a-input-number>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CheckShort",
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