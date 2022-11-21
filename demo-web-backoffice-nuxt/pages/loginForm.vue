<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';
    async function login() {
        const loginId = document.querySelector("#id") as HTMLInputElement;
        const loginPw = document.querySelector("#pw") as HTMLInputElement;

        if (loginId.value == null || loginId.value == "" || loginId.value == undefined) {
            alert("ID를 입력해주세요");
            return false;
        } else if (loginPw.value == null || loginPw.value == "" || loginPw.value == undefined) {
            alert("PW를 입력해주세요");
            return false;
        }

        const response = await axios.get('http://demo.shop.bo.com:8000/api/system/login?id='+loginId.value+'&pw='+loginPw.value);
        console.log(response);

    }
</script>
<template>
    <div>
        <span>ID : </span>
        <input type="text" id="id" placeholder="ID를 입력하세요"/>
        <span>PW : </span>
        <input type="password" id="pw" placeholder="PW를 입력하세요"/>
        <button @click="login()">로그인</button>
    </div>
</template>
<script lang="ts">
export default {
  layout: 'default'  
};
</script>