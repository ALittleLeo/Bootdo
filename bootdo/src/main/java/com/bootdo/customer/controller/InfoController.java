package com.bootdo.customer.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.customer.domain.InfoDO;
import com.bootdo.customer.service.InfoService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-11-09 19:42:07
 */
 
@Controller
@RequestMapping("/customer/info")
public class InfoController {
	@Autowired
	private InfoService infoService;
	
	@GetMapping()
	@RequiresPermissions("customer:info:info")
	String Info(){
	    return "customer/info/info";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("customer:info:info")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InfoDO> infoList = infoService.list(query);
		int total = infoService.count(query);
		PageUtils pageUtils = new PageUtils(infoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("customer:info:add")
	String add(){
	    return "customer/info/add";
	}

	@GetMapping("/edit/{customerId}")
	@RequiresPermissions("customer:info:edit")
	String edit(@PathVariable("customerId") Integer customerId,Model model){
		InfoDO info = infoService.get(customerId);
		model.addAttribute("info", info);
	    return "customer/info/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("customer:info:add")
	public R save( InfoDO info){
		if(infoService.save(info)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("customer:info:edit")
	public R update( InfoDO info){
		infoService.update(info);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("customer:info:remove")
	public R remove( Integer customerId){
		if(infoService.remove(customerId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("customer:info:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] customerIds){
		infoService.batchRemove(customerIds);
		return R.ok();
	}
	
}
