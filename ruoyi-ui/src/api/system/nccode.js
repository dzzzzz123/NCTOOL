import request from '@/utils/request'

// 上传NC代码
export function uploadNcCode(data) {
  return request({
    url: '/system/nccode/upload',
    method: 'post',
    data: data
  })
}

export function transFormNcCode(tapNames) {
  return request({
    url: '/system/nccode/transform/' + tapNames,
    method: 'get',
  })
}

export function newTapList(tapNames) {
  return request({
    url: '/system/nccode/newTapList/' + tapNames,
    method: 'get',
  })
}

export function compareDownload(oldFileName, newFileName) {
  const data = {
    oldFileName,
    newFileName
  }
  return request({
    url: '/system/nccode/compare',
    method: 'put',
    data: data
  })
}

export function uploadToDNC(tapNames) {
  return request({
    url: '/system/nccode/ToDNC'+tapNames,
    method: 'get'
  })
}