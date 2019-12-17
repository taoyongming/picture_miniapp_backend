package web.Configurator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import web.entity.ResponseModel;
import web.util.R;
import web.util.RRException;

/**
 * 异常处理器
 * 
 * @author tym
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
		@ExceptionHandler(RRException.class)
		public R handleRRException(RRException e){
			R r = new R();
			r.put("errorCode", e.getCode());
			r.put("msg", e.getMessage());

		return r;
	}
	@ExceptionHandler(RuntimeException.class)
	public R handleRuntimeException(RuntimeException e){

		return R.error(e.getMessage());
	}



	@ExceptionHandler(DuplicateKeyException.class)
	public  R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}



	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
		logger.error(e.getMessage(), e);
		return  R.error("页面请求参数错误");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}

	@ResponseBody
	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	public ResponseModel handle(MaxUploadSizeExceededException e) {
		ResponseModel model = ResponseModel.getInstance();
		if (e instanceof MaxUploadSizeExceededException) {
			model.setMessage("上传的文件超过大小限制");
		}else{
			model.setMessage("上传失败");
		}
		return model;
	}
}
