<template>
    <div>
      <el-select v-model="selectedValue">
        <el-option v-for="(item, index) in dict" 
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
        >{{ item.dictLabel }}</el-option>
      </el-select>
    </div>
  </template>
  <script>
  import { listData, getDicts } from "@/api/system/dict/data";
import { watch } from "less";
  export default {
    props: {
      dictName: {
        type: String,
        required: true
      },
      initialValue: {
        type: String,
        default: null
      }
    },
    data() {
      return {
        selectedValue: this.initialValue,
        queryParams: {dictType: this.dictName},
        dict: {}
      };
    },
    watch: {
      selectedValue(newValue) {
        this.$emit("input", newValue);
      }
    },
    created() {      
        this.getDictionary(this.dictName);
    },
    methods: {
      getDictionary(name) {
        getDicts(name).then(response => {
            this.dict = response.data;

            // 如果字典数据获取成功，且selectedValue在字典中有效，则更新selectedValue
            if (this.initialValue && this.dict.some(item => item.dictValue === this.initialValue)) {
              this.selectedValue = this.initialValue; 
            } else {
              this.selectedValue = null; // 或者可以默认设置为其他值
            }
        });
      }
    }
  };
  </script>