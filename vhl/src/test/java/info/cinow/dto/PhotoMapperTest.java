package info.cinow.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.audit.Audit;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
// import info.cinow.model.User;
import info.cinow.model.User;

/**
 * PhotoMapperTest
 */
@RunWith(SpringRunner.class)
public class PhotoMapperTest {

    @MockBean
    public PhotoMapper<PhotoDto> photoMapper;

    @MockBean
    public PhotoMapper<PhotoSaveDto> photoSaveMapper;

    @MockBean
    public PhotoMapper<PhotoAdminDto> photoAdminMapper;
    Photo photo;

    Photo dtoPhoto;

    Photo saveDtoPhoto;

    Photo adminDtoPhoto;

    PhotoDto dto;

    PhotoSaveDto saveDto;

    PhotoAdminDto adminDto;

    @Before
    public void setup() {
        CensusTract tract = new CensusTract();
        tract.setGid(1);

        User user = new User();
        user.setFirstName("First");
        user.setLastName("last");
        Audit audit = new Audit();
        audit.setLastModified(LocalDateTime.now());
        audit.setLastModifiedBy(user);

        this.photo = new Photo();
        this.photo.setAudit(audit);
        this.photo.setApproved(true);
        this.photo.setDescription("Description");
        this.photo.setId(1L);
        this.photo.setFileName("fileName");
        this.photo.setCensusTract(tract);
        this.photo.setOwnerEmail("email");
        this.photo.setOwnerFirstName("First");
        this.photo.setOwnerLastName("Last");

        this.dto = new PhotoDto();
        this.dto.setApproved(true);
        this.dto.setDescription("Description");
        this.dto.setId(1L);
        this.dto.setFileName("fileName");
        this.dto.setCensusTractId(tract.getGid());

        this.dtoPhoto = new Photo();
        this.dtoPhoto.setApproved(true);
        this.dtoPhoto.setDescription("Description");
        this.dtoPhoto.setId(1L);
        this.dtoPhoto.setFileName("fileName");
        this.dtoPhoto.setCensusTract(tract);

        this.saveDto = new PhotoSaveDto();
        this.saveDto.setDescription("Description");
        this.saveDto.setId(1L);
        this.saveDto.setOwnerEmail("email");
        this.saveDto.setOwnerFirstName("First");
        this.saveDto.setOwnerLastName("Last");
        this.saveDto.setLastEditedBy(audit.getLastModifiedBy().toString());
        this.saveDto.setLastEdited(audit.getLastModified().toString());

        this.saveDtoPhoto = new Photo();
        this.saveDtoPhoto.setApproved(true);
        this.saveDtoPhoto.setDescription("Description");
        this.saveDtoPhoto.setId(1L);
        this.saveDtoPhoto.setFileName("fileName");
        this.saveDtoPhoto.setOwnerEmail("email");
        this.saveDtoPhoto.setOwnerFirstName("First");
        this.saveDtoPhoto.setOwnerLastName("Last");

        this.adminDto = new PhotoAdminDto();
        this.adminDto.setApproved(true);
        this.adminDto.setDescription("Description");
        this.adminDto.setId(1L);
        this.adminDto.setFileName("fileName");
        this.adminDto.setOwnerEmail("email");
        this.adminDto.setOwnerFirstName("First");
        this.adminDto.setCensusTractId(tract.getGid());
        this.adminDto.setOwnerLastName("Last");
        // this.adminDto.setLastEditedBy(audit.getLastModifiedBy().toString());
        this.adminDto.setLastEdited(audit.getLastModified().format(DateTimeFormatter.ISO_LOCAL_DATE).toString());

        this.adminDtoPhoto = new Photo();
        this.adminDtoPhoto.setApproved(true);
        this.adminDtoPhoto.setDescription("Description");
        this.adminDtoPhoto.setId(1L);
        this.adminDtoPhoto.setFileName("fileName");
        this.adminDtoPhoto.setOwnerEmail("email");
        this.adminDtoPhoto.setOwnerFirstName("First");
        this.adminDtoPhoto.setOwnerLastName("Last");
        this.adminDtoPhoto.setCensusTract(tract);

    }

    @Test
    public void photoMapper_ReturnsDto() {
        PhotoDto returnDto = this.photoMapper.toDto(photo).orElse(null);
        assertNotNull(returnDto);
        assertEquals(dto, returnDto);
    }

    @Test
    public void photoMapper_ReturnsPhoto() {
        Photo returnPhoto = this.photoMapper.toPhoto(dto).orElse(null);
        assertNotNull(returnPhoto);
        assertEquals(dtoPhoto, returnPhoto);
    }

    @Test
    public void photoMapper_ReturnsEmptyPhoto() {
        Photo returnPhoto = this.photoMapper.toPhoto(null).orElse(null);
        assertNull(returnPhoto);
    }

    @Test
    public void photoMapper_ReturnsEmptyDto() {
        PhotoDto returnDto = this.photoMapper.toDto(null).orElse(null);
        assertNull(returnDto);
    }

    @Test
    public void photoSaveMapper_ReturnsDto() {
        PhotoSaveDto returnDto = this.photoSaveMapper.toDto(photo).orElse(null);
        assertNotNull(returnDto);
        assertEquals(returnDto, saveDto);
    }

    @Test
    public void photoSaveMapper_ReturnsPhoto() {
        Photo returnPhoto = this.photoSaveMapper.toPhoto(saveDto).orElse(null);
        assertNotNull(returnPhoto);
        assertEquals(returnPhoto, saveDtoPhoto);
    }

    @Test
    public void photoSaveMapper_ReturnsEmptyPhoto() {
        Photo returnPhoto = this.photoSaveMapper.toPhoto(null).orElse(null);
        assertNull(returnPhoto);
    }

    @Test
    public void photoSaveMapper_ReturnsEmptyDto() {
        PhotoSaveDto returnDto = this.photoSaveMapper.toDto(null).orElse(null);
        assertNull(returnDto);
    }

    @Test
    public void photoAdminMapper_ReturnsDto() {
        PhotoAdminDto returnDto = this.photoAdminMapper.toDto(photo).orElse(null);
        assertNotNull(returnDto);
        assertEquals(adminDto, returnDto);
    }

    @Test
    public void photoAdminMapper_DtoHasCorrectDateFormat() {
        PhotoAdminDto returnDto = this.photoAdminMapper.toDto(photo).orElse(null);
        assertNotNull(returnDto);
        assertEquals(photo.getAudit().getLastModified().format(DateTimeFormatter.ISO_LOCAL_DATE).toString(),
                returnDto.getLastEdited());
    }

    @Test
    public void photoAdminMapper_ReturnsPhoto() {
        Photo returnPhoto = this.photoAdminMapper.toPhoto(adminDto).orElse(null);
        assertNotNull(returnPhoto);
        assertEquals(returnPhoto, adminDtoPhoto);
    }

    @Test
    public void photoAdminMapper_ReturnsEmptyPhoto() {
        Photo returnPhoto = this.photoAdminMapper.toPhoto(null).orElse(null);
        assertNull(returnPhoto);
    }

    @Test
    public void photoAdminMapper_ReturnsEmptyDto() {
        PhotoAdminDto returnDto = this.photoAdminMapper.toDto(null).orElse(null);
        assertNull(returnDto);
    }

}