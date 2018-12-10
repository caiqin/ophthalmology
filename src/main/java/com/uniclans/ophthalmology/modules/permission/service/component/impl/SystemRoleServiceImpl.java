package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemRole;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemRoleRepository;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemRoleVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemRoleService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;

@Service
public class SystemRoleServiceImpl implements ISystemRoleService {

	@Resource
	private ISystemRoleRepository systemRoleDao;
	@Resource
	private SystemRoleVoConverter systemRoleDoConverter;

	@Override
	@Transactional
	public List<SystemRoleVo> getSystemRoleDos(SystemRoleVo systemRoleDo)
			throws Exception {
		final String roleName = systemRoleDo.getRoleName();
		List<SystemRole> list = systemRoleDao.findAll(new Specification<SystemRole>(){
            @Override
            public Predicate toPredicate(Root<SystemRole> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	Predicate p1 = criteriaBuilder.like(root.get("deleteFlag").as(String.class), SystemManagerConstants.DELETE_FLAG_0);
            	if(roleName!=null&&!roleName.equals("")){
            		Predicate p2 = criteriaBuilder.like(root.get("cityName").as(String.class), "%"+roleName+"%");
            		query.where(criteriaBuilder.and(p1,p2));
            	}else {
            		query.where(criteriaBuilder.and(p1));
            	}
                return query.getRestriction();
            }
        });
		return systemRoleDoConverter.rolePo2VoList(list);
	}

	@Override
	@Transactional
	public void addSystemRole(SystemRoleVo systemRoleDo) throws Exception {
		SystemRole systemRole = systemRoleDoConverter
				.systemRoleVo2Po(systemRoleDo);
		systemRole.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_0);
		systemRole.setCreateTime(DateUtils.getCurrentDateTime());
		systemRoleDao.save(systemRole);
	}

	@Override
	@Transactional
	public SystemRoleVo getSystemRoleVo(String systemRoleId) throws Exception {
		return systemRoleDoConverter.systemRolePo2Vo(systemRoleDao.getOne(systemRoleId));
	}

	@Override
	@Transactional
	public void updateSystemRole(SystemRoleVo systemRoleDo) throws Exception {
		systemRoleDo.setUpdateTime(DateUtils.getCurrentDateTime());
		SystemRole systemRole = systemRoleDao.getOne(systemRoleDo.getId());
		systemRole.setIsValid(systemRoleDo.getIsValid());
		systemRole.setRemark(systemRoleDo.getRemark());
		systemRole.setRoleName(systemRoleDo.getRoleName());
	}

	@Override
	@Transactional
	public void delSystemRole(SystemRoleVo systemRoleDo) throws Exception {
		systemRoleDo.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_0);
		SystemRole systemRole = systemRoleDoConverter
				.systemRoleVo2Po(systemRoleDo);
		systemRoleDao.delete(systemRole);
	}

	@Override
	@Transactional
	public PageFinder<SystemRoleVo> getRoles(SystemRoleQueryVo queryVo,
			int pageNo, int pageSize) throws Exception {
//		String roleName = queryVo.getRoleName();
//		Criteria criteria = systemRoleDao.createCriteria();
//		if(roleName!=null&&!roleName.isEmpty()){
//			criteria.add(Restrictions.like("roleName", "%"+roleName+"%"));
//		}
		final String roleName = queryVo.getRoleName();
		Specification<SystemRole> sf = new Specification<SystemRole>() {
			@Override
			public Predicate toPredicate(Root<SystemRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate stuNameLike = null;
				if (roleName!=null&&!roleName.isEmpty()) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					stuNameLike = cb.like(root.<String>get("roleName"), "%" + roleName + "%");
				}
				if (null != stuNameLike)
					query.where(cb.and(stuNameLike));
				return null;
			}
		};
		Pageable pageable = new PageRequest(pageNo, pageSize);
		Page<SystemRole> pages = systemRoleDao.findAll(sf, pageable);		
		return new PageFinder<SystemRoleVo>(pageNo, pageSize, (int)pages.getTotalElements(), systemRoleDoConverter.rolePo2VoList(pages.getContent()));
	}

	@Override
	@Transactional
	public List<SystemRoleVo> getSystemRoleByIds(List<String> roleIds)
			throws Exception {
		List<SystemRoleVo> doList = new ArrayList<SystemRoleVo>();
		if (roleIds != null && roleIds.size() > 0) {
			for (String roleid : roleIds) {
				SystemRole systemRole = systemRoleDao.findOne(roleid);
				if (systemRole != null
						&& systemRole.getIsValid().equals(
								SystemManagerConstants.SYSTEM_ROLE_ISVALID_1)) {
					doList.add(systemRoleDoConverter
							.systemRolePo2Vo(systemRole));
				}
			}
		}
		return doList;
	}

	@Override
	@Transactional
	public boolean checkRoleExist(SystemRoleVo systemRoleDo) throws Exception {
		String roleName = systemRoleDo.getRoleName();
		if (roleName != null && !"".equals(roleName)) {
			SystemRole systemRole = new SystemRole();
			systemRole.setRoleName(roleName);
			Example<SystemRole> example = Example.of(systemRole);
			return systemRoleDao.exists(example);
		}
		return false;
	}

}
