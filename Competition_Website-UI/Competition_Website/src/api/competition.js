import request from '@/http/'

export function applyCompetition(data) {
    return request({
        method: "POST",
        url: '/apply',
        data,
    });
}

export function submitWorks(data) {
    return request({
        method: "POST",
        url: "/submit",
        data
    })
}