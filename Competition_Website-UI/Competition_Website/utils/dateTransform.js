/**
 * 时间格式转换类
 */
import moment from "moment"
const dateTransform = (date) => {
    return moment(new Date(date)).format("YYYY-MM-DD");
}
export default {
    dateTransform
}