package web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.ImagesMapper;
import web.entity.Images;
import web.service.ImageService;

/**
 * DESCRIPTION
 *
 * @author tym
 * @ceeate 2019/12/18
 **/
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public void save(Images images) {
        imagesMapper.insert(images);

    }
}
