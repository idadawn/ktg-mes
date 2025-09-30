<template>
    <div class="app-container">      
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="检验单编号" prop="ipqcCode">
              <el-input v-model="form.ipqcCode" placeholder="请输入检验单编号" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item  label-width="80">
              <el-switch v-model="autoGenFlag"
                  active-color="#13ce66"
                  active-text="自动生成"
                  @change="handleAutoGenChange(autoGenFlag)" v-if="optType != 'view' && form.status =='PREPARE'">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检验单名称" prop="ipqcName">
              <el-input v-model="form.ipqcName" placeholder="请输入检验单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="检验类型" prop="ipqcType">
              <el-select v-model="form.ipqcType" placeholder="请选择检验类型">
                <el-option
                  v-for="dict in dict.type.mes_pqc_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="检测结果" prop="checkResult">
              <el-select v-model="form.checkResult" placeholder="请选择检验结果">
                <el-option
                  v-for="dict in dict.type.mes_qc_result"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="检测人员" prop="inspector">
              <el-input v-model="form.inspector" placeholder="请选择检测人员" >
                <el-button slot="append" @click="handleUser2Select" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <UserSingleSelect ref="user2Select" @onSelected="onUser2Selected"></UserSingleSelect>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
            <el-form-item label="检测数量" prop="quantityCheck">
              <el-input v-model="form.quantityCheck" placeholder="请输入检测数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合格品数量" prop="quantityQualified">
                <el-input-number v-model="form.quantityQualified" :min="0" :max="99999999" placeholder="请输入合格品数量" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="不合格数" prop="quantityUnqualified">
                <el-input-number v-model="form.quantityUnqualified" :min="0" :max="99999999" placeholder="请输入不合格数" />
            </el-form-item>
            </el-col>
        </el-row>
        <el-row v-if="form.quantityUnqualified >0">
          <el-col :span="8">
            <el-form-item label="工废" prop="quantityLaborScrap">
              <el-input-number :min="0" @change="handleScrapChanged" v-model="form.quantityLaborScrap" placeholder="请输入工废数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="料废" prop="quantityMaterialScrap">
              <el-input-number :min="0" @change="handleScrapChanged" v-model="form.quantityMaterialScrap" placeholder="请输入料废数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="其他" prop="quantityOtherScrap">
              <el-input-number :min="0" @change="handleScrapChanged" v-model="form.quantityOtherScrap" placeholder="请输入其他原因废品数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="工单编码" prop="workorderCode">
              <el-input v-if="form.ipqcId == null" v-model="form.workorderCode" placeholder="请选择生产工单" >
              </el-input>
              <!--保存过则不允许修改工单，需要修改则删除重做-->
              <el-input v-else v-model="form.workorderCode" >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作站编号" prop="workstationCode">
              <el-input v-model="form.workstationCode" placeholder="请输入工作站编号" >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作站名称" prop="workstationName">
              <el-input v-model="form.workstationName" readonly="readonly"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
            <el-form-item label="产品编码" prop="itemCode">
                <el-input v-model="form.itemCode" placeholder="请输入产品编码" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="产品名称" prop="itemName">
                <el-input v-model="form.itemName" placeholder="请输入产品名称" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="单位" prop="unitOfMeasure">
                <el-input v-model="form.unitOfMeasure" placeholder="请输入单位" />
            </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
            <el-form-item label="规格型号" prop="specification">
                <el-input v-model="form.specification" type="textarea" placeholder="请输入内容" />
            </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
            <el-form-item label="工序编码" prop="processCode">
                <el-input v-model="form.processCode" readonly="readonly" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="工序名称" prop="processName">
                <el-input v-model="form.processName" readonly="readonly" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="任务编号" prop="taskCode">
                <el-input v-model="form.taskCode" placeholder="请输入任务编号" />
            </el-form-item>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="8">
            <el-form-item label="致命缺陷数量" prop="crQuantity">
                <el-input-number v-model="form.crQuantity" :max="99999999" placeholder="请输入致命缺陷数量" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="严重缺陷数量" prop="majQuantity">
                <el-input-number v-model="form.majQuantity" :max="99999999" placeholder="请输入严重缺陷数量" />
            </el-form-item>
            </el-col>
            <el-col :span="8">
            <el-form-item label="轻微缺陷数量" prop="minQuantity">
                <el-input-number v-model="form.minQuantity" :max="99999999" placeholder="请输入轻微缺陷数量" />
            </el-form-item>
            </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="检测日期" prop="inspectDate">
              <el-date-picker clearable
                v-model="form.inspectDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择检测日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider v-if="form.ipqcId !=null" content-position="center">检测项</el-divider>
      <el-card shadow="always" v-if="form.ipqcId !=null" class="box-card">
          <Ipqcline ref=line :ipqcId="form.ipqcId" :optType="optType"></Ipqcline>
      </el-card>
      <el-form label-width="100px">
        <el-form-item style="text-align: center;margin-left:-120px;margin-top:30px;">
            <el-button type="primary" @click="cancel" v-if="optType =='view' || form.status !='PREPARE' ">返回</el-button>
            <el-button type="primary" @click="submitForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
            <el-button type="success" @click="handleFinish" v-if="form.status =='PREPARE' && optType !='view'  && form.ipqcId !=null">完成</el-button>
            <el-button @click="cancel">取 消</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>
<script>
import { listIpqc, getIpqc, delIpqc, addIpqc, updateIpqc } from "@/api/mes/qc/ipqc";
import {genCode} from "@/api/system/autocode/rule"
import WorkorderSelect from "@/components/workorderSelect/single.vue"
import WorkstationSelect from "@/components/workstationSelect/simpletableSingle.vue"
import UserSingleSelect from "@/components/userSelect/single.vue"
import Ipqcline from "../ipqc/line.vue"
export default {
    name: "IpqcAdd",
    components: {WorkorderSelect,WorkstationSelect,Ipqcline,UserSingleSelect},
    dicts: ['mes_pqc_type','mes_qc_result','mes_order_status'],
    props: {
        sourceDocId: null,
        sourceDocCode: null,
        sourceDocType: null,
        sourceLineId: null,
        qcDetailType:null,
        workOrderId: null,
        workOrderCode: null,
        workOrderName: null,
        workstationId: null,
        workstationCode: null,
        workstationName: null,
        taskId: null,
        taskCode: null,
        taskName: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        unitName: null,
        batchId: null,
        batchCode: null,
        quantityCheck: null
    },
    data() {
        return {
            autoGenFlag: false,
            optType: 'add',
            // 表单参数
            form: {},
            // 表单校验
            rules: {
              ipqcCode: [
                { required: true, message: "请输入或生产检验单编号", trigger: "blur" },
                { max: 64, message: "字段过长", trigger: "blur" }
              ],
              ipqcName: [
                { max: 100, message: "字段过长", trigger: "blur" }
              ],
              specification: [
                { max: 250, message: "字段过长", trigger: "blur" }
              ],
              processCode: [
                { max: 64, message: "字段过长", trigger: "blur" }
              ],
              processName: [
                { max: 100, message: "字段过长", trigger: "blur" }
              ],
              inspector: [
                { max: 32, message: "字段过长", trigger: "blur" }
              ],
              remark: [
                { max: 250, message: "字段过长", trigger: "blur" }
              ],
              ipqcType: [
                { required: true, message: "请选择检验类型", trigger: "change" }
              ],             
              quantityCheck: [
                { required: true, message: "检测数量不能为空", trigger: "blur" }
              ],
              quantityUnqualified: [
                { required: true, message: "不合格品数量不能为空", trigger: "blur" }
              ],
              checkResult: [
                { required: true, message: "请选择检测结果", trigger: "blur" }
              ],
              quantityQualified: [
                { required: true, message: "合格品数量不能为空", trigger: "blur" }
              ]
            }
        }
    },
    created() {
        this.reset();
    },
    methods: {
        // 取消按钮
        cancel() {
            const obj = { path: "/mes/qc/pendinginspect" };
            this.$tab.closeOpenPage(obj);
        },
        // 表单重置
        reset() {
            this.form = {
                ipqcId: null,
                ipqcCode: null,
                ipqcName: null,
                ipqcType: this.$route.params.qcDetailType,
                templateId: null,
                sourceDocId: this.$route.params.sourceDocId,
                sourceDocType: this.$route.params.sourceDocType,
                sourceDocCode: this.$route.params.sourceDocCode,
                sourceLineId: this.$route.params.sourceLineId,
                workorderId: this.$route.params.workOrderId,
                workorderCode: this.$route.params.workOrderCode,
                workorderName: this.$route.params.workOrderName,
                taskId: this.$route.params.taskId,
                taskCode: this.$route.params.taskCode,
                taskName: this.$route.params.taskName,
                workstationId: this.$route.params.workstationId,
                workstationCode: this.$route.params.workstationCode,
                workstationName: this.$route.params.workstationName,
                processId: null,
                processCode: null,
                processName: null,
                itemId: this.$route.params.itemId,
                itemCode: this.$route.params.itemCode,
                itemName: this.$route.params.itemName,
                specification: this.$route.params.specification,
                unitOfMeasure: this.$route.params.unitOfMeasure,
                unitName: this.$route.params.unitName,
                quantityCheck: this.$route.params.quantityCheck,
                quantityUnqualified: null,
                quantityQualified: null,
                quantityLaborScrap: 0,
                quantityMaterialScrap: 0,
                quantityOtherScrap: 0,
                crRate: null,
                crRate: null,
                majRate: null,
                minRate: null,
                crQuantity: null,
                majQuantity: null,
                minQuantity: null,
                checkResult: null,
                inspectDate: new Date(),
                inspector: null,
                status: "PREPARE",
                remark: null,
                attr1: null,
                attr2: null,
                attr3: null,
                attr4: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null
            };
            this.autoGenFlag = false;
            this.resetForm("form");
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.ipqcId != null) {
                        updateIpqc(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                        });
                    } else {
                        addIpqc(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.form.ipqcId = response.data; //将返回的ID保存
                        });
                    }
                }
            });
        },
        //点击完成
        handleFinish(){
            let that = this;
            if(this.form.checkResult == null){
                this.$modal.msgError("请选择检测结果！");
                return;
            }

            this.$refs["form"].validate(valid => {
                    if (valid) {
                        this.$modal.confirm('是否完成检验单编制？【完成后将不能更改】').then(function(){
                            that.form.status = 'FINISHED';
                            that.submitForm();
                            that.cancel();
                        });
                    }
                });
        },
        //点击人员选择按钮
        handleUser2Select(){
            this.$refs.user2Select.showFlag = true;
        },
        //人员选择返回
        onUser2Selected(row){            
            this.form.inspector = row.nickName;
        },

        handleScrapChanged(){
          this.form.quantityUnqualified = this.form.quantityLaborScrap + this.form.quantityMaterialScrap + this.form.quantityOtherScrap;
        },

        //自动生成编码
        handleAutoGenChange(autoGenFlag){
            if(autoGenFlag){
                genCode('QC_PQC_CODE').then(response =>{
                this.form.ipqcCode = response;
                });
            }else{
                this.form.ipqcCode = null;
            }
        }
    }
}
</script>