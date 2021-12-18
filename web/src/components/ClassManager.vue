<template>
    <div>
        <div>
            <a-button type="primary" @click="addClass">添加班级</a-button>
        </div>
        <div style="margin: 10px 0">
            <a-input v-model="search" placeholder="请输入班级名称" style="width: 20%"></a-input>
            <a-button type="primary" style="margin-left: 5px" @click="load">查询</a-button>
        </div>
        <div>
            <a-table
                    :columns="columns"
                    :data-source="data"
                    :pagination="pagination"
                    :rowKey="(record)=>{return record.id}"
                    @change="handleTableChange"
            >
                                <span slot="action" slot-scope="text, record">
                                  <a-button size="small" type="primary" @click="updateClass(record)">修改</a-button>
                                  <a-divider type="vertical" />
                                  <a-button size="small" type="danger" @click="showConfirm(record.id)">删除</a-button>
                                </span>
            </a-table>
            <a-modal
                    title="添加班级"
                    :visible="visible1"
                    :confirm-loading="confirmLoading"
                    @ok="handleOk"
                    @cancel="handleCancel"
                    okText="确定"
                    cancelText="取消"
            >
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                    <a-form-item label="班级名称">
                        <a-input
                                v-decorator="['name', { rules: [{ required: true, message: '请输入班级名称！' }] }]"
                                placeholder="请填写班级名称"
                        />
                    </a-form-item>
                </a-form>
            </a-modal>
            <a-modal
                    title="修改班级信息"
                    :visible="visible2"
                    :confirm-loading="confirmLoading"
                    @ok="handleOk"
                    @cancel="handleCancel"
                    okText="确定"
                    cancelText="取消"
            >
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                    <a-form-item label="班级名称">
                        <a-input
                                v-decorator="['name', { rules: [{ required: true, message: '请输入班级名称！' }] }]"
                                placeholder="请填写班级名称"
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
        return axios.get('/getAllClasses', { params: params });
    };
    export default {
        name: "ClassManager",
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
                        title: '班级名称',
                        dataIndex: 'name',
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
            showConfirm(id){
                var that = this
                this.$confirm({
                    title: '提示',
                    content: '删除该班级后，与该班级有关的记录将删除',
                    okText:'确定',
                    cancelText:'取消',
                    onOk() {
                        axios.delete("/deleteClass",{
                            params:{
                                id
                            }
                        }).then(res=>{
                            if (res.data.code==="0") {
                                that.$message.success("删除班级成功")
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
            addClass(){
                this.visible1 = true
            },
            updateClass(record){
                this.visible2 = true
                // record是对象，传址，直接用会改变原数据，要用JSON.parse
                var data = JSON.parse(JSON.stringify(record));
                this.$nextTick(()=>{
                    this.form.setFieldsValue({name:data.name})
                    this.temp = data
                })
            },
            handleOk(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        if (this.temp===''){
                            axios.post("/saveClass",values).then(res=>{
                                if (res.data.code === "0"){
                                    this.fetch(this.pagination)
                                    this.$message.success("新增班级成功")
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
                            values.id = this.temp.id
                            axios.put("/updateClass",values,{headers: {
                                    'Content-Type': 'application/json'
                                }}).then(res=>{
                                if (res.data.code === "0"){
                                    this.fetch(this.pagination)
                                    this.$message.success("修改班级信息成功")
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
            // 查询一个班级
            load(){
                axios.get("/findOneClass",{
                    params:{
                        search:this.search
                    }
                }).then(res=>{
                    this.data.length = 0
                    this.data.push(res.data.data)
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