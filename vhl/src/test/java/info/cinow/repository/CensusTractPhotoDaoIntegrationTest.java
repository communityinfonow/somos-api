package info.cinow.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import info.cinow.model.CensusTract;
import info.cinow.model.Photo;

/**
 * CensusTractPhotoDaoIntegrationTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class CensusTractPhotoDaoIntegrationTest {

    @Autowired
    private CensusTractPhotoDao dao;

    @Autowired
    private PhotoDao photoDao;

    private CensusTract censusTract;

    private Photo photo;

    @Before
    public void setup() {
        this.censusTract = new CensusTract(1);
        this.photo = new Photo();
        this.photo.setApproved(false);
        this.photo.setCensusTract(censusTract);
        this.photo.setDescription("");
        this.photo.setFileName("temp_file");
        this.photoDao.save(photo);

    }

    @Test
    public void getAllPhotosForCensusTract() {
        List<Photo> photos = this.dao.findPublicByCensusTractId(this.censusTract.getGid());
        assertFalse(photos.isEmpty());
        assertTrue(photos.contains(photo));

    }

    @Test
    public void getPhotoByCensusTractIdPhotoId() {
        Photo queriedPhoto = this.dao.findPublicByCensusTractPhotoId(this.censusTract.getGid(), this.photo.getId());
        assertNotNull(queriedPhoto);
        assertEquals(photo.getId(), queriedPhoto.getId());
    }

}