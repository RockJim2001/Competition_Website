/**
 * 文件预览
 * @param {文件存储地址} url 
 * @returns 
 */
export function preview(url) {
    return 'http://127.0.0.1:8012/onlinePreview?url=' + encodeURIComponent(window.btoa(url))
}