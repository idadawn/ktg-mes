<template>
  <el-dialog title="安灯呼叫配置"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="60%"
    center
  >
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:pro:andonconfig:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="andonconfigList">
      <el-table-column type="index" />
      <el-table-column label="呼叫原因" :render-header="addRedStar" align="center" prop="andonReason" >
        <template slot-scope="scope">
            <el-input type="textarea" v-model="scope.row.andonReason"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="级别" :render-header="addRedStar" align="center" prop="andonLevel" >
        <template slot-scope="scope">
            <el-select v-model="scope.row.andonLevel">
                <el-option
                    v-for="dict in dict.type.mes_andon_level"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />                
            </el-select>          
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center" prop="handlerRoleName" >
        <template slot-scope="scope">
          <el-select v-model="scope.row.handlerRoleName" @change="handleRoleSelected(scope.row)"   placeholder="请选择角色">
                <el-option
                  v-for="item in rolesOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleName"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
        </template>
      </el-table-column>
      <el-table-column label="处置人" align="center" prop="handlerNickName" >
        <template slot-scope="scope">
          <el-input v-model="scope.row.handlerNickName" placeholder="请选择处置人" >
            <el-button slot="append" @click="handleUserSelect(scope.$index)" icon="el-icon-search"></el-button>
          </el-input>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index,scope.row)"
            v-hasPermi="['mes:pro:andonconfig:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirm">确 定</el-button>
        <el-button @click="showFlag=false">关 闭</el-button>
    </div>
    <UserSingleSelect ref="userSelect" @onSelected="onUserSelected"></UserSingleSelect>
  </el-dialog>


</template>

<script>
import { listAndonconfig, updateAndonconfig, delAndonconfig} from "@/api/mes/pro/andonconfig";
import { listRole} from "@/api/system/role";
import UserSingleSelect from "@/components/userSelect/single.vue"
export default {
  name: "Andonconfig",
  components: {UserSingleSelect},
  dicts: ['mes_andon_level'],
  props: {
    configId: null,
    optType:null
  },
  watch :{
      configId(){
        this.queryParams.configId = this.configId;        
      }    
  },
  data() {
    return {
      showFlag: false,
      selectedRow: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 安灯呼叫配置表格数据
      andonconfigList: [],
      rolesOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        configId: null,
        andonReason: null,        andonLevel: null,        handlerRoleId: null,        handlerRoleName: null,        handlerUserId: null,        handlerUserName: null,        handlerNickName: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        andonReason: [
          { required: true, message: "呼叫原因不能为空", trigger: "blur" }
        ],        andonLevel: [
          { required: true, message: "级别不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
    this.getRoles();
  },
  methods: {
    addRedStar(h,{ column }){
      return [
        h('span', {style: "color:red"}, "*"),
        h('span', " " + column.label)
      ];
    },
    handleOpen() {
      this.showFlag = true
      this.getList();
      this.getRoles();
    },
    getRoles(){
      listRole().then(response => {
        this.rolesOptions = response.rows;
      });
    },
    /** 查询安灯呼叫配置列表 */
    getList() {
      this.loading = true;
      listAndonconfig(this.queryParams).then(response => {
        this.andonconfigList = response.data;        
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.showFlag = false;
    },
    // 表单重置
    reset() {
      this.form = {
        configId: null,        andonReason: null,        andonLevel: null,        handlerRoleId: null,        handlerRoleName: null,        handlerUserId: null,        handlerUserName: null,        handlerNickName: null,        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      var row = {
          configId: null,        
          andonReason: null,        
          andonLevel: null,        
          handlerRoleId: null,        
          handlerRoleName: null,        
          handlerUserId: null,        
          handlerUserName: null,        
          handlerNickName: null,        
          remark: null,        
          createTime: null,        
          updateBy: null,        
          updateTime: null,
          flag: 'add' 
        }
        this.andonconfigList.unshift(row);
    },
    /** 提交按钮 */
    confirm() {
        if (this.andonconfigList.length > 0) {
            this.loading = true;
            let flag = true;
            this.andonconfigList.forEach(item => {
                if(item.andonReason == null || item.andonReason == ""){
                    this.$message.error("呼叫原因不能为空");
                    this.loading = false;
                    flag = false;
                    return;
                }
                if(item.andonLevel == null || item.andonLevel == ""){
                    this.$message.error("级别不能为空");
                    this.loading = false;
                    flag = false;
                    return;
                }
                if((item.handlerNickName == null || item.handlerNickName == "") && (item.handlerRoleId == null || item.handlerRoleId == "")){
                    this.$message.error("请选择处置人或角色");
                    this.loading = false;
                    flag = false;
                    return;
                }
            })
            if(flag){
              updateAndonconfig(this.andonconfigList).then(res => {
                this.$modal.msgSuccess("修改成功");
                if (res.code === 200) {
                  this.showFlag = false;
                  this.getList();
                }
              },err => {
                this.loading = false;
              });
            }
        }
    },
    /** 删除按钮操作 */
    handleDelete(index,row) {      
        const configIds = row.configId || this.ids;
        let that = this;
        this.$modal.confirm('确认删除配置项？').then(function() {
            that.andonconfigList.forEach(function(item,i,arr){
                if(i == index){
                  if(that.andonconfigList[index].flag=='add'){
                    //如果是刚新增的则直接删除
                    that.andonconfigList.splice(index,1);
                  }else{
                    //如果是从数据库查询的则从数据库删除
                    delAndonconfig(configIds).then(res=>{
                      that.getList();
                    });
                  }                                      
                }
            })
        });
    },
    //点击人员选择按钮
    handleUserSelect(index){
      this.selectedRow = index;
      this.$refs.userSelect.showFlag = true;
    },
    onUserSelected(row){
      this.andonconfigList[this.selectedRow].handlerNickName = row.nickName;
      this.andonconfigList[this.selectedRow].handlerUserId = row.userId;
      this.andonconfigList[this.selectedRow].handlerUserName = row.userName;
    },
    handleRoleSelected(row){
      const role =this.rolesOptions.find(item => item.roleName === row.handlerRoleName);
      if(role){
        row.handlerRoleId = role.roleId;
      }      
    }
  }
};
</script>
