package com.ruoyi.zayy.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.mapper.CheckRecordMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.service.ICheckUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人员管理Controller
 * 
 * @author jgkj
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/zayy/checkUser")
public class CheckUserController extends BaseController
{
    @Autowired
    private ICheckUserService checkUserService;
    @Autowired
    CheckUserMapper checkUserMapper;
    @Autowired
    CommonMapper commonMapper;
    /**
     * 查询人员管理列表
     */
//    @PreAuthorize("@ss.hasPermi('zayy:checkUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckUser checkUser)
    {
        Integer roleId = checkUser.getRoleId();
        System.out.println(roleId);
        String userDept = checkUser.getSysUserDept();
        if(roleId == null){
            startPage();
            List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
            return getDataTable(list);
        }
        switch (roleId){
            case 1:  //超级管理员可以看到所有的巡检人员
            case 6:  //院领导可以看到所有巡检人员
            case 2: {  //管理员可以看到所有的巡检人员
                startPage();
                List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
                return getDataTable(list);
            }
            case 7:{  //巡检员可以看到自己的巡检人员
                startPage();
                List<CheckUser> list = checkUserMapper.selectBackByUserId(checkUser);
                return getDataTable(list);
            }
            case 3:{ //科室主任可以看到他科室的巡检人员
                if(userDept.contains(",")){
                    List<CheckUser> list = checkUserMapper.selectBackByKszr(checkUser);
                    HashSet set = new HashSet();

                    String[] placeIdsStrSimple = userDept.split(",");
                    for (int k = 0; k < placeIdsStrSimple.length; k++) {
                        String userDeptSimple = placeIdsStrSimple[k];
                        for (int i = 0; i < list.size(); i++) {
                            CheckUser checkUser2 = list.get(i);
                            String dept = checkUser2.getUserDept();
                            if(dept.contains(",")){ //查出来的多科室
                                String[] placeIdsStr = dept.split(",");
                                for (int j = 0; j < placeIdsStr.length; j++) {
//                                placeIds[j] = Long.valueOf(placeIdsStr[j]);
                                    if(userDeptSimple.equals(placeIdsStr[j])){
                                        set.add(checkUser2.getId());
                                    }
                                }
                            }else {//查出来的单科室
                                if(userDeptSimple.equals(dept)){
                                    set.add(checkUser2.getId());
                                }
                            }
                        }
                    }
                    Long[] userIds = new Long[set.size()];
                    Object[] it = set.toArray();
                    for (int i = 0; i < it.length; i++) {
                        userIds[i] = (Long) it[i];
                    }
                    startPage();
                    list = checkUserMapper.selectBackByUserIds(userIds);
                    return getDataTable(list);
                }else {
                    //查询用户单科室
//                    startPage();
                    List<CheckUser> list = checkUserMapper.selectBackByKszr(checkUser);
                    for (int i = 0; i < list.size(); i++) {
                        CheckUser checkUser2 = list.get(i);
                        String dept = checkUser2.getUserDept();
                        if(dept.contains(",")){ //查出来的多科室
                            String[] placeIdsStr = dept.split(",");
                            Integer strLength = 0;
                            for (int j = 0; j < placeIdsStr.length; j++) {
//                                placeIds[j] = Long.valueOf(placeIdsStr[j]);
                                if(userDept.equals(placeIdsStr[j])){
                                    strLength++;
                                }
                            }
                            if(strLength == 0){
                                list.remove(i);
                                i--;
                            }
                        }else {
                            Integer strLength = 0;
                            if(userDept.equals(dept)){
                                strLength++;
                            }
                            if(strLength == 0){
                                list.remove(i);
                                i--;
                            }
                        }
                    }
                    Long[] userIds = new Long[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        userIds[i] = list.get(i).getId();
                    }
                    startPage();
                    list = checkUserMapper.selectBackByUserIds(userIds);
                    return getDataTable(list);
                }
            }
        }
        startPage();
        List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
        return getDataTable(list);
    }

    /**
     * 导出人员管理列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkUser:export')")
    @Log(title = "人员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckUser checkUser)
    {
        List<CheckUser> list = checkUserService.selectCheckUserList(checkUser);
        ExcelUtil<CheckUser> util = new ExcelUtil<CheckUser>(CheckUser.class);
        util.exportExcel(response, list, "人员管理数据");
    }

    /**
     * 获取人员管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkUserService.selectCheckUserById(id));
    }

    /**
     * 新增人员管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkUser:add')")
    @Log(title = "人员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckUser checkUser)
    {
        try{
            checkUserService.insertCheckUser(checkUser);
        }catch (Throwable throwable){
            return AjaxResult.error("用户名"+checkUser.getUserName()+"已存在");
        }
        return toAjax(1);
    }


    /**
     * 修改人员管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkUser:edit')")
    @Log(title = "人员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckUser checkUser)
    {
        SysUser user = new SysUser();
        user.setNickName(checkUser.getNickName());
        user.setUserName(checkUser.getUserName());
        user.setUserDept(checkUser.getUserDept());
        user.setOfficeId(checkUser.getOfficeId());
        user.setPassword(checkUser.getUserPassword());
        user.setDingUserId(checkUser.getDingUserId());
        user.setRoleId(getUserRole(Long.valueOf(checkUser.getUserRole())));
        Integer checkUserRoleInt = checkUser.getUserRole();
        Long checkUserRole = Long.valueOf(checkUserRoleInt);
        Long userRole = getUserRole(checkUserRole);
        user.setRoleId(userRole);
        Long[] userRoleIds = new Long[]{userRole};
        user.setRoleIds(userRoleIds);
        try{
            checkUserMapper.updateSysUser(user);
        }catch (Throwable throwable){
            System.out.println("当前用户不存在");
        }
        return toAjax(checkUserService.updateCheckUser(checkUser));
    }
    public Long getUserRole(Long checkUserRole){
        Long userRole = null;
        if(checkUserRole == 0){    //巡检员
            userRole = 7l;
        }
        if(checkUserRole == 2){    //科室主任
            userRole = 3l;
        }
        if(checkUserRole == 3){    //职能科室
            userRole = 5l;
        }
        if(checkUserRole == 4){    //院领导
            userRole = 6l;
        }
        return userRole;
    }

    /**
     * 删除人员管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkUser:remove')")
    @Log(title = "人员管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        JSONArray userNameArray = new JSONArray();
        for (int i = 0; i < ids.length; i++) {
            Long checkUserId = ids[i];
            CheckUser checkUser =  checkUserService.selectCheckUserById(checkUserId);
            String userName = checkUser.getUserName();
            userNameArray.add(userName);
        }
        if (userNameArray.size() > 0) {
            checkUserMapper.deleteSysuser(userNameArray);
        }
        return toAjax(checkUserService.deleteCheckUserByIds(ids));
    }

    @PostMapping("/getAllCheckUser")
    public List<CheckUser> getAllCheckUser(){
        return checkUserMapper.selectCheckUserAll();
    }


}
