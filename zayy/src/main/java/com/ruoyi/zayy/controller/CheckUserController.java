package com.ruoyi.zayy.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.zayy.mapper.CheckUserMapper;
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

    /**
     * 查询人员管理列表
     */
//    @PreAuthorize("@ss.hasPermi('zayy:checkUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckUser checkUser)
    {
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
        return toAjax(checkUserService.insertCheckUser(checkUser));
    }

    @Autowired
    private CheckUserMapper checkUserMapper;

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
}
