import request from '@/http/'

export function getCompetitionInfoById(data) {
    return request({
        method: "post",
        url: "/competitionInfoById",
        data
    })
}