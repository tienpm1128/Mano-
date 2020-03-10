package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultObjectBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import com.viettel.nfv.vdashboardx.utils.Constants;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class NSDService implements INSDService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Autowired
    private IUpdatePartObjectService updatePartObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private IDownloadFileService downloadFileService;

    @Value("${spring.multipart.max-file-size}")
    private String maxSize;

    private static Logger logger = LoggerFactory.getLogger(NSDService.class.getName());

    @Override
    public Mono<ResultBO> getListOfNSDs(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getNSDDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createNSD(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> uploadContentNSD(String path, RequestContent requestContent, long contentLenght) throws IOException {
        ResultBO objDto = new ResultObjectBO();

        return requestContent.getFilePart().flatMap(file -> {
            String filename = file.filename();
            String extension = FilenameUtils.getExtension(filename);

            if (!extension.equalsIgnoreCase("CSAR")) {
                objDto.setError(true);
                objDto.setErrorCode(Constants.N_OK);
                objDto.setMessage("The file is not properly formatted!");

                return Mono.just(objDto);
            } else if (contentLenght > Long.parseLong(maxSize)) {
                objDto.setError(true);
                objDto.setErrorCode(Constants.N_OK);
                objDto.setMessage("File size exceeds 10mb!");

                return Mono.just(objDto);
            }

            try {
                return uploadFileService.uploadFile(path, requestContent, ResultType.OBJECT_TYPE, HttpMethod.PUT);
            } catch (IOException e) {
                logger.info("Error: {}", e);
            }

            return Mono.just(objDto);
        });
    }

    @Override
    public Mono<Resource> downloadContentNSD(String path, RequestContent requestContent) throws IOException {
        return downloadFileService.downloadFile(path, requestContent);
    }

    @Override
    public Mono<ResultBO> updateNSD(String path, RequestContent requestContent) throws IOException {
        return updatePartObjectService.patchObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> deleteNSD(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getTotalNsd(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }
}