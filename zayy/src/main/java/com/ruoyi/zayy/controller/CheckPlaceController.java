package com.ruoyi.zayy.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckItem;
import com.ruoyi.zayy.mapper.CheckItemMapper;
import com.ruoyi.zayy.util.QRCodeUtil;
import netscape.javascript.JSObject;
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
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.service.ICheckPlaceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检地点Controller
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@RestController
@RequestMapping("/zayy/checkPlace")
public class CheckPlaceController extends BaseController
{
    @Autowired
    private ICheckPlaceService checkPlaceService;

    /**
     * 查询巡检地点列表
     */
//    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckPlace checkPlace)
    {
        startPage();
        List<CheckPlace> list = checkPlaceService.selectCheckPlaceList(checkPlace);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPlaceImg(RuoYiConfig.getApiImgUrl()+"/"+list.get(i).getPlaceImg());
        }
        return getDataTable(list);
    }

    /**
     * 导出巡检地点列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:export')")
    @Log(title = "巡检地点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckPlace checkPlace)
    {
        List<CheckPlace> list = checkPlaceService.selectCheckPlaceList(checkPlace);
        ExcelUtil<CheckPlace> util = new ExcelUtil<CheckPlace>(CheckPlace.class);
        util.exportExcel(response, list, "巡检地点数据");
    }

    /**
     * 获取巡检地点详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkPlaceService.selectCheckPlaceById(id));
    }

    /**
     * 新增巡检地点
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:add')")
    @Log(title = "巡检地点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckPlace checkPlace)throws Exception
    {
        String placeId = String.valueOf(UUID.randomUUID());
//        String redirect_uri = java.net.URLEncoder.encode(RuoYiConfig.getApiServer(),"utf-8");
        String url = RuoYiConfig.getWebUrl()+"/"+"?placeId="+placeId+"&corpId=ding9c8ccab5f3f96c1435c2f4657eb6378f";
        String logoPath = RuoYiConfig.getLogoUrl();
        String destPath = RuoYiConfig.getCheckImg();
        String fileName = QRCodeUtil.encode(url,logoPath,destPath,true);

//        try {
//            generateQRCodeImage(placeId, 500, 500, RuoYiConfig.getCheckImg()+fileName);
//        } catch (WriterException e) {
//            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
//        }
        checkPlace.setPlaceId(placeId);
        checkPlace.setPlaceImg(fileName);
        return toAjax(checkPlaceService.insertCheckPlace(checkPlace));
    }



    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    /**
     * 修改巡检地点
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:edit')")
    @Log(title = "巡检地点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckPlace checkPlace)
    {
        return toAjax(checkPlaceService.updateCheckPlace(checkPlace));
    }

    /**
     * 删除巡检地点
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkPlace:remove')")
    @Log(title = "巡检地点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkPlaceService.deleteCheckPlaceByIds(ids));

    }

    public void test()throws Exception{
        String redirect_uri = java.net.URLEncoder.encode("http://zayy.fgimax.vipnps.vip/ding/auth","utf-8");
        String url = "http://inspection.fby.jiaguangkeji.com"+"/"+"?placeId="+"sasa"+"&redirect_uri="+redirect_uri
                +"&response_type=code&client_id=" + "ding7azaat1uqapjxt39"
                +"&scope=openid&state=dddd&prompt=consent";
        System.out.println(url);
    }
    @Autowired
    CheckItemMapper checkItemMapper;

    //根据巡检地点ID查询巡检项
    @PostMapping("/getItems")
    public List getItems(@RequestBody JSONObject jsonObject){
        String placeId = jsonObject.getString("placeId");
        CheckItem checkItem = new CheckItem();
        checkItem.setTiemCommon(0);
        List<HashMap> itemListCommon = checkItemMapper.selectCheckItemListHashMap(checkItem);
        List<HashMap> itemListSpecial = checkItemMapper.selectItemSpecial(placeId);
        List<HashMap> items = new ArrayList<>();
        if (itemListCommon.size() > 0) {
            for (int i = 0; i < itemListCommon.size(); i++) {
                HashMap itemMap = itemListCommon.get(i);
                HashMap addMap = new HashMap();
                addMap.put("item_id", itemMap.get("id"));
                addMap.put("item_name", itemMap.get("item_name"));
                addMap.put("item_abnormal", itemMap.get("item_abnormal"));
                items.add(addMap);
            }
        }
        if(itemListSpecial.size() > 0){
            for (int i = 0; i < itemListSpecial.size(); i++) {
                HashMap itemMap = itemListSpecial.get(i);
                HashMap addMap = new HashMap();
                addMap.put("item_id", itemMap.get("id"));
                addMap.put("item_name", itemMap.get("item_name"));
                addMap.put("item_abnormal", itemMap.get("item_abnormal"));
                items.add(addMap);
            }
        }
        return items;
    }

}
