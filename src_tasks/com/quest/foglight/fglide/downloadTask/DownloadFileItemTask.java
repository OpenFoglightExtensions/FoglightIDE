package com.quest.foglight.fglide.downloadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.quest.wcf.core.component.AbstractTaskComponent;
import com.quest.wcf.core.component.ActionContext;
import com.quest.wcf.core.module.config.PropertyAccessor;
import com.quest.wcf.core.module.config.wcfdo.ConfigDataObject;
import com.quest.wcf.core.module.runtimevalue.RuntimeValueException;

public class DownloadFileItemTask extends AbstractTaskComponent {

	public String handleAction(ActionContext ctx) throws IOException {
		ConfigDataObject configuration = getTask().getConfiguration();

		
		String fi = null;
		
		try {
			fi = (String) PropertyAccessor.get(ctx, configuration, "file");
		} catch (RuntimeValueException e) {
			// TODO Auto-generated catch block
			return "Error";
			
			
		}
		
		String name = new File(fi).getName();
		ctx.sendRedirect("/../fglide/dl?filename=" + name+"&path="+fi);
		return "OK";
	}

}
