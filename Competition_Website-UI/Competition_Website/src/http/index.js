import axios from 'axios'
import QS from 'qs';
import {
    ElMessage
} from 'element-plus'
import store from '../../store/index'

const service = axios.create({
    baseURL: 'http://localhost:8088/',
    timeout: 5000,
})

service.interceptors.request.use(
    config => {
        // if (store.getters['login/token']) {
        //     config.headers['Authorization'] = store.getter['login/token']
        // }
        if (config.method === "POST") {
            config.data = QS.stringify(config.data)
        }
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)


service.interceptors.response.use(
    response => {
        const res = response.data
        if (!res.success) {
            ElMessage({
                message: res.message || 'Error',
                type: 'error',
                duration: 5 * 1000
            })
            // return Promise.reject(new Error("flag:" + res.message || 'Error'))
            return res
        } else {
            return res
        }
    },
    error => {
        console.log('err' + error)
        ElMessage({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service