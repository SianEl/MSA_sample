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
    props: {
        prevUrl: String
    },
    layout: 'default',
    setup(props, context) {
        console.log(props.prevUrl)
    },
    methods: {
        login() {
            let id = document.getElementById("id").value;
            let pw = document.getElementById("pw").value;

            
        }
    },
    mounted() {
        
    }
}
</script>