<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';
    const route = useRoute();
    const router = useRouter();
    const prevUrl = ref(route.query.prevUrl ? route.query.prevUrl : "/");

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

        const { data, pending, error, refresh } = await useFetch('/api/system/login', {
            method: 'get',
            server: false,
            params: {
                id: loginId.value,
                pw: loginPw.value
            },
            onRequestError({request, options, error}) {
                console.log("onRequestError");
                console.log(error);
            },
            onResponseError({request, response, error}) {
                console.log("onResponseError");
                console.log(response);
                console.log(error);
            },
            onResponse({ request, response, options}) {
                console.log("onResponse");
                let result = response._data;
                if(result?.errorCode == 0) {
                    router.push("/").then();
                } else {
                    alert(result?.errorMessage);
                }
                
            }
        });

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