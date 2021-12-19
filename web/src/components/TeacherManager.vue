<template>
    <div>
        <div>
            <a-button type="primary" @click="addTeacher">添加账号</a-button>
        </div>
        <div style="margin: 10px 0">
            <a-input v-model="search" placeholder="请输入工号/姓名" style="width: 20%"></a-input>
            <a-button type="primary" style="margin-left: 5px" @click="load">查询</a-button>
        </div>
        <div>
            <a-table
                    :columns="columns"
                    :data-source="data"
                    :pagination="pagination"
                    :rowKey="(record)=>{return record.no}"
                    @change="handleTableChange"
            >
                                <span slot="action" slot-scope="text, record">
                                  <a-button size="small" type="primary" @click="updateTeacher(record)">修改</a-button>
                                  <a-divider type="vertical" />
                                  <a-button size="small" type="danger" @click="showConfirm(record.no)">删除</a-button>
                                </span>
            </a-table>
            <a-modal
                    title="添加教师"
                    :visible="visible1"
                    :confirm-loading="confirmLoading"
                    @ok="handleOk"
                    @cancel="handleCancel"
                    okText="确定"
                    cancelText="取消"
            >
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                    <a-form-item label="工号">
                        <a-input
                                v-decorator="['no', { rules: [{ required: true, message: '工号只包括数字，长度为8！', pattern: /^[0-9]{8}$/ }] }]"
                                placeholder="请填写教师工号"
                        />
                    </a-form-item>
                    <a-form-item label="姓名">
                        <a-input
                                v-decorator="['name', { rules: [{ required: true, message: '请输入姓名！' }] }]"
                                placeholder="请填写教师姓名"
                        />
                    </a-form-item>
                    <a-form-item label="性别">
                        <a-radio-group v-decorator="['sex',{ rules: [{ required: true, message: '请选择性别！' }] }]">
                            <a-radio :value=1>
                                男
                            </a-radio>
                            <a-radio :value=2>
                                女
                            </a-radio>
                        </a-radio-group>
                    </a-form-item>
                    <a-form-item label="初始密码">
                        <a-input
                                v-decorator="['password', { rules: [{ required: true, message: '密码必须由字母或数字组成，长度6-12！', pattern: /^[\w]{6,12}$/}] }]"
                                placeholder="请输入初始密码"
                        />
                    </a-form-item>
                </a-form>
            </a-modal>
            <a-modal
                    title="修改教师信息"
                    :visible="visible2"
                    :confirm-loading="confirmLoading"
                    @ok="handleOk"
                    @cancel="handleCancel"
                    okText="确定"
                    cancelText="取消"
            >
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                    <a-form-item label="工号">
                        <a-input
                                v-decorator="[
                                    'no',
                                     { rules: [{ required: true, message: '工号只包括数字，长度为8！', pattern: /^[0-9]{8}$/ }] }]"
                                placeholder="请填写教师工号"
                        />
                    </a-form-item>
                    <a-form-item label="姓名">
                        <a-input
                                v-decorator="['name', { rules: [{ required: true, message: '请输入姓名！' }] }]"
                                placeholder="请填写教师姓名"
                        />
                    </a-form-item>
                    <a-form-item label="性别">
                        <a-radio-group v-decorator="['sex',{ rules: [{ required: true, message: '请选择性别！' }] }]">
                            <a-radio :value=1>
                                男
                            </a-radio>
                            <a-radio :value=2>
                                女
                            </a-radio>
                        </a-radio-group>
                    </a-form-item>
                    <a-form-item label="密码">
                        <a-input
                                v-decorator="['password', { rules: [{ required: true, message: '密码必须由字母或数字组成，长度6-12！', pattern: /^[\w]{6,12}$/}] }]"
                                placeholder="请输入账号密码"
                        />
                    </a-form-item>
                </a-form>
            </a-modal>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import qs from "qs";

    const queryData = params => {
        return axios.get('/getAllTeachers', { params: params });
    };
    export default {
        name: "TeacherManager",
        data(){
            return{
                confirmLoading:false,
                visible1:false,
                visible2:false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
                data:[],
                loading:false,
                search:'',
                pagination:{
                    pageNum:1,
                    pageSize:6
                },
                columns:[
                    {
                        title: '教师工号',
                        dataIndex: 'no',
                        defaultSortOrder: 'ascend',
                        sorter:true
                    },
                    {
                        title: '姓名',
                        dataIndex: 'name',
                    },
                    {
                        title: '性别',
                        dataIndex: 'sex'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ],
                temp:''
            }
        },
        mounted() {
            axios.get("/allClasses").then(res=>{
                if (res.data.code==="0"){
                    this.classes = res.data.data
                }
                else
                    this.$message.error(res.data.msg)
            })
            this.fetch(this.pagination);
        },
        watch:{
          search(){
              if (this.search===''){
                  this.fetch(this.pagination)
              }
          }
        },
        methods:{
            showConfirm(no){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该教师后，与该教师有关的记录将删除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/deleteTeacher",{
                            params:{
                                no
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除教师成功")
                                that.fetch(that.pagination)
                            }
                            else {
                                that.$message.error(res.data.msg)
                            }
                        })
                    },
                    onCancel() {},
                });
            },
            addTeacher(){
                this.visible1 = true
            },
            updateTeacher(record){
                this.visible2 = true
                // record是对象，传址，直接用会改变原数据，要用JSON.parse
                var data = JSON.parse(JSON.stringify(record));
                this.$nextTick(()=>{
                    if (data.sex==='男')
                        data.sex = 1
                    else
                        data.sex = 2
                    this.form.setFieldsValue(data)
                    this.temp = data
                })
            },
            handleOk(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        if (this.temp===''){
                            axios.post("/saveTeacher",values).then(res=>{
                                if (res.data.code === "0"){
                                    this.fetch(this.pagination)
                                    this.$message.success("新增教师成功")
                                    this.confirmLoading = true;
                                    this.visible1 = false;
                                    this.confirmLoading = false;
                                    this.form.resetFields()
                                }
                                else{
                                    this.$message.error(res.data.msg)
                                }
                            })
                        }
                        else {
                            let data = JSON.stringify({oldValue:this.temp,newValue:values})
                            axios.put("/updateTeacher",data,{headers: {
                                    'Content-Type': 'application/json'
                                }}).then(res=>{
                                if (res.data.code === "0"){
                                    this.fetch(this.pagination)
                                    this.$message.success("修改教师信息成功")
                                    this.confirmLoading = true;
                                    this.visible2 = false;
                                    this.confirmLoading = false;
                                    this.temp = ''
                                    this.form.resetFields()
                                }
                                else{
                                    this.$message.error(res.data.msg)
                                }
                            })

                        }

                    }
                });

            },
            handleCancel(e) {
                this.temp = ''
                this.visible1 = false;
                this.visible2 = false;
                this.form.resetFields()
            },
            // 查询一个教师
            load(){
                axios.get("/findTeacherByKey",{
                    params:{
                        search:this.search
                    }
                }).then(res=>{
                    this.data.length = 0
                    this.data = res.data.data
                    this.data = this.data.filter(n => n)
                    this.parseSex()
                })
            },
            handleTableChange(pagination, filters, sorter){
                const pager = { ...this.pagination };
                pager.current = pagination.current;
                this.pagination = pager;
                this.fetch({// 根据pagination改变后的参数查询
                    pageSize: pagination.pageSize,   // 页面大小
                    pageNum: pagination.current,       // 单前页码
                    sortField: sorter.field,
                    sortOrder: sorter.order,
                });
            },
            fetch(params = {}) {
                this.loading = true;
                queryData({
                    pageSize: 6,
                    ...params,
                }).then(({ data }) => {
                    const pagination = { ...this.pagination };
                    // 从后端获取数据总量total
                    pagination.total = data.data.total
                    this.loading = false;
                    // 将数据传给data
                    this.data = data.data.records;
                    this.parseSex()
                    this.pagination = pagination;
                });
            },
            parseSex(){
                this.data.forEach((d)=>{
                    if (d.sex===1)
                        d.sex = '男'
                    else
                        d.sex = '女'
                })
            }
        }
    }
</script>

<style scoped>

</style>