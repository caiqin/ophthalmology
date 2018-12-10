package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.Md5Utils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.basecomponent.utils.VoPoConverter;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.UserRole;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemUserRepository;
import com.uniclans.ophthalmology.modules.permission.dao.repository.IUserRoleRepository;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemUserVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserQueryVo;

@Service
public class SystemUserServiceImpl implements ISystemUserService {

	@Resource
	private EntityManager entityManager;
	@Resource
	private ISystemUserRepository systemUserDao;
	@Resource
	private SystemUserVoConverter systemUserDoConverter;

	@Resource
	private IUserRoleRepository userRoleDao;

	@Transactional(readOnly = true)
	@Override
	public SystemUserVo getSystemUserByName(String loginName, String loginPassword) throws Exception {
		Map<String, Object> values = new HashMap<>();
		values.put("loginName", loginName);
		values.put("loginPassword", Md5Utils.encryptMD5(loginPassword));
		SystemUser systemUser = systemUserDao.findByLoginNameAndLoginPassword(loginName,Md5Utils.encryptMD5(loginPassword));
		return systemUserDoConverter.systemUserPo2Vo(systemUser);
	}

	@Transactional(readOnly = true)
	@Override
	public SystemUserVo getSystemUserById(String userId) throws Exception {
		return systemUserDoConverter.systemUserPo2Vo(systemUserDao.findOne(userId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemUserVo> getAllUsers() throws Exception {
		SystemUser systemUser = new SystemUser();
		systemUser.setDeleteFlag("0");
		Example<SystemUser> example = Example.of(systemUser);
		List<SystemUser> poList = systemUserDao.findAll(example);
		return systemUserDoConverter.userPo2VoList(poList);
	}

	@Override
	@Transactional
	public SystemUserVo saveOrUpdateUser(SystemUserVo systemUserDo) throws Exception {
		SystemUser systemUser = systemUserDoConverter.systemUserVo2Po(systemUserDo);
		systemUser.setUserNo(CreatorNoUtil.getCode());
		systemUser.setLoginPassword(Md5Utils.encryptMD5(systemUserDo.getLoginPassword()));
		SystemUser returnUser = systemUserDao.save(systemUser);
		return systemUserDoConverter.systemUserPo2Vo(returnUser);
	}

	@Override
	@Transactional
	public SystemUserVo deleteUser(SystemUserVo systemUserDo) throws Exception {
		SystemUser user = systemUserDao.findOne(systemUserDo.getId());
		user.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_1);
		systemUserDo.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_1);
		return systemUserDo;
	}

	@Override
	@Transactional(readOnly = true)
	public PageFinder<SystemUserVo> getUsers(UserQueryVo userQueryVo, Integer pageNo, Integer pageSize)
			throws Exception {
		final String name = StringUtils.parseStrNull(userQueryVo.getUserName());
		final String loginName = StringUtils.parseStrNull(userQueryVo.getLoginName());
		Specification<SystemUser> sf = new Specification<SystemUser>() {
			@Override
			public Predicate toPredicate(Root<SystemUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate userNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					userNameLike = cb.like(root.<String>get("userName"), "%" + name + "%");
				}
				Predicate loginNameLike = null;
				if (!loginName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					loginNameLike = cb.like(root.<String>get("loginName"), "%" + loginName + "%");
				}
				if (null != userNameLike&&null == loginNameLike) {
					query.where(cb.and(userNameLike));
				}else if (null == userNameLike&&null != loginNameLike) {
					query.where(cb.and(loginNameLike));
				}else if(null != userNameLike&&null != loginNameLike) {
					query.where(cb.and(userNameLike,loginNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.DESC, "createDate");
		Page<SystemUser> pages = systemUserDao.findAll(sf, pageable);		
		PageFinder<SystemUser> pageFinder = new PageFinder<SystemUser>(pageNo, pageSize, (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<SystemUserVo> data = systemUserDoConverter.userPo2VoList(pageFinder.getData());
			return new PageFinder<SystemUserVo>(pageNo, pageSize, pageFinder.getRowCount(), data);
		}
		return new PageFinder<SystemUserVo>(pageNo, pageSize, 0);
	}

	@Override
	public List<SystemUserVo> getAUsersForType(String loginType) throws Exception {
		SystemUser user = new SystemUser();
		user.setDeleteFlag("0");
		user.setLoginType(loginType);
		Example<SystemUser> example = Example.of(user);
		List<SystemUser> poList = systemUserDao.findAll(example);
		return systemUserDoConverter.userPo2VoList(poList);
	}

	@Override
	@Transactional
	public boolean checkUserExist(SystemUserVo systemUserDo) throws Exception {
		String loginName = systemUserDo.getLoginName();
		if (loginName != null && !"".equals(loginName)) {
			SystemUser user = new SystemUser();
			user.setLoginName(loginName);
			Example<SystemUser> example = Example.of(user);
			return systemUserDao.exists(example);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean checkMobileExist(SystemUserVo systemUserDo) throws Exception {
		String mobile = systemUserDo.getMobilePhone();
		if (mobile != null && !"".equals(mobile)) {
			SystemUser user = new SystemUser();
			user.setMobilePhone(mobile);
			Example<SystemUser> example = Example.of(user);
			return systemUserDao.exists(example);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean unLockOrlockUser(String userId) throws Exception {
		SystemUser entity = systemUserDao.findOne(userId);

		if (entity != null && entity.getUserState().equals("1")) {
			entity.setUserState("0");
		} else {
			entity.setUserState("1");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean remotePassword(SystemUserVo systemUserDo) throws Exception {
		SystemUser user = systemUserDao.findOne(systemUserDo.getId());
		user.setLoginPassword(Md5Utils.encryptMD5(systemUserDo.getLoginPassword()));
		return true;
	}

	@Override
	@Transactional
	public boolean editPassword(String id, String password) throws Exception {
		SystemUser user = systemUserDao.findOne(id);
		user.setLoginPassword(password);
		return true;
	}

	/**
	 * 根据用户角色查询该角色所有用户信息
	 * 
	 * @param roleId
	 *            角色id
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, SystemUserVo> getUsersByRoleId(String roleId) throws Exception {
		List<UserRole> userRoles = userRoleDao.findByRoleId(roleId);
		Map<String, SystemUserVo> map = new HashMap<String, SystemUserVo>();
		if (userRoles != null) {
			for (UserRole userRole : userRoles) {
				SystemUser systemUser = systemUserDao.findOne(userRole.getUserId());
				if (systemUser != null) {
					map.put(systemUser.getLoginName(), VoPoConverter.copyProperties(systemUser, SystemUserVo.class));
				}
			}
		}
		return map;
	}

	@Override
	@Transactional
	public void relation(SystemUserVo systemUserVo) throws Exception {
		String userNo = systemUserVo.getUserNo();
		String doctorId = systemUserVo.getDoctorId();
		String doctorName = systemUserVo.getDoctorName();
		SystemUser systemUser = systemUserDao.findByUserNo(userNo);
		systemUser.setDoctorId(doctorId);
		systemUser.setDoctorName(doctorName);
	}

	@Override
	@Transactional
	public boolean updatePassword(SystemUserVo systemUserDo) throws Exception {
		SystemUser user = systemUserDao.findByLoginNameAndLoginPassword(systemUserDo.getLoginName(),Md5Utils.encryptMD5(systemUserDo.getOldPassword()));
		if(user==null) {
			throw new Exception("原始密码错误");
		}else {
			user.setLoginPassword(Md5Utils.encryptMD5(systemUserDo.getLoginPassword()));
		}
		return true;
	}
}
