package com.viettel.vocs.service.ServiceManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.service.Common.IGetListObjectService;

import reactor.core.publisher.Mono;


@Service
public class ManoService implements IManoService {

	@Autowired
	private IGetListObjectService getListObjectService;

	@Override
	public Mono<ResultBO> getListOfManoService(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

}
