import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';

dayjs.locale('zh-cn');

/**
 * 
 * @param {*} time 时间戳
 * @param {*} format 格式化 YYYY-MM-DD HH:mm:ss
 * @returns 
 */
export function formatDate(time, format = 'YYYY-MM-DD HH:mm:ss') {
    return dayjs(time).format(format)
}
export function formatDate_YMD(time, format = 'YYYY-MM-DD') {
    return dayjs(time).format(format)
}