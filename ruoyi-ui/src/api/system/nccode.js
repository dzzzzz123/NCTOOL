import request from '@/utils/request'

// 上传NC代码
export function uploadNcCode(data) {
  return request({
    url: '/system/nccode/upload',
    method: 'post',
    data: data,
    timeout: 100000 // 100秒
  })
}

export function uploadPdf(data) {
  return request({
    url: '/system/nccode/upload',
    method: 'post',
    data: data,
    timeout: 100000 // 100秒
  })
}

export function transFormNcCode(tapNames) {
  return request({
    url: '/system/nccode/transform/' + tapNames,
    method: 'get',
    timeout: 100000 // 100秒
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
    url: '/system/nccode/ToDNC/'+tapNames,
    method: 'get',
    timeout: 100000 // 100秒
  })
}

export function getTapNames(tapName){
  return request({
    url: '/system/nccode/getTapName/'+tapName,
    method: 'get'
  })
}

export function insertTapNames(tapNames){
  return request({
    url: '/system/nccode/insertTapNames/'+tapNames,
    method: 'get'
  })
}
