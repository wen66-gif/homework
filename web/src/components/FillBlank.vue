<template>
    <div >
        <a-form

                :form="form"
                @submit="handleSubmit"
                :label-col="{ span: 2 }"
                :wrapper-col="{ span: 15 }"
        >
            <a-form-item label="分数">
                <a-input-number
                        :min="0"
                        v-decorator="['score', { rules: [{ required: true, message: '请输入分数！' }] }]"/>
            </a-form-item>
            <a-form-item label="题干">
                <a-textarea
                        v-decorator="['title', { rules: [{ required: true, message: '请输入题干！' }] }]"
                        :auto-size="{ minRows: 3, maxRows: 5 }"
                />
            </a-form-item>
            <div>
                <label style="color: black;margin-left: 10px">正确答案</label>
                <span style="color: red;margin-left: 10px;font-size: 10px"></span>
            </div>
            <a-form-item  v-for="(t,index) in blankId" :key="t.id" :label="['（'+(index+1)+'）']" :wrapper-col="{ span: 15 }" style="">
                <!--用t.id作为每个textarea的标识，可以起到唯一效果，避免数据改变时页面重新渲染内容出错
                    删除非最后元素时，
                        用index作别名，会使下面所有已输入的数据丢失
                        用t.title作别名，数据不会丢失，但再添加一空时，数据会跟上一个元素重复，（有可能我的title没有更改）但是文本框重新生成的
                    猜测：
                        重新渲染时，数据跟当前数据别名比较，
                        其实就是别名重复，重复时数据重复，不重复就都是

                    -->
                <a-textarea
                        style="width: 600px;"
                        v-decorator="[`${t.id}`, { rules: [{ required: true, message: '请输入正确答案！' }] }]"
                        :auto-size="{ minRows: 3, maxRows: 5 }"
                />
                    <a-icon  type="close" @click="delBlank(index)" style="line-height:80px;margin-left: 30px;"/>


            </a-form-item>
        </a-form>
        <a-button icon="plus-circle" shape="round" size="large" @click="add" style="margin-left: 30%">添加一空</a-button>
        <a-button shape="round" type="primary" size="large" style="margin-left: 30px" @click="handleSubmit">保存题目</a-button>
    </div>
</template>

<script>
    import {mapActions} from "vuex";
    import {nanoid} from 'nanoid'
    export default {
        name: "FillBlank",
        data(){
            return{
                blankContent:[],
                id:'',
                form: this.$form.createForm(this, { name: 'coordinated' }),
                blankId:[
                    { id:nanoid()},
                ]
            }
        },

        watch:{
            // 观察到blankContent发生改变，也就是进行更新
            blankContent(){
                var value = {}
                // 将id content存到value中
                for (let i = 0;i<this.blankId.length;i++){
                    var id  = this.blankId[i].id
                    value[id] = this.blankContent[i]
                }
                console.log(value)
                // 等待页面渲染完成
                this.$nextTick(()=>{
                    // 将数据渲染到表单
                    this.form.setFieldsValue(value)
                })
            }
        },
        methods:{
            ...mapActions(['addOrUpdate']),
            add(){
                this.blankId.push({id:nanoid()})
                console.log(this.blankId)
            },
            delBlank(index){
                if (this.blankId.length-1<=0) {
                    this.$message.error("至少有一空")
                }
                else {
                    this.blankSum--
                    this.blankId.splice(index,1)
                    console.log(this.blankId)
                }
            },
            handleSubmit(){
                this.form.validateFields((err, values) => {
                    if (!err) {
                        // 将每空答案汇总合并
                        let trueAnswer = []
                        console.log(this.blankId)
                        for(let i = 0;i < this.blankId.length;i++){
                            let id = this.blankId[i].id
                            // 将values中的属性值拿出来
                            trueAnswer.push(values[id])
                            // 删除key为当前id的属性
                            delete values[id]
                        }
                        trueAnswer = trueAnswer.join("_")
                        values.type = 4
                        values.id = this.id
                        values.trueAnswer = trueAnswer
                        this.addOrUpdate(values)
                        this.$message.success("保存成功")
                        this.form.resetFields()
                        this.blankSum = 1
                        this.blankId = [{id:nanoid()}]

                        console.log('Received values of form: ', values);
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>