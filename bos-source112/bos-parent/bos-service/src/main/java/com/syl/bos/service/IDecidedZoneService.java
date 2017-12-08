package com.syl.bos.service;

import com.syl.bos.utils.PageBean;
import com.syl.domain.Decidedzone;

public interface IDecidedZoneService {

	void save(Decidedzone model, String[] subareaid);

	void pageQuery(PageBean pageBean);

}
