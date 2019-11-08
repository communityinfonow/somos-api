package info.cinow.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.repository.CensusTractDao;

/**
 * CensusTractMapperTest TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CensusTractMapperTest {

    @Autowired
    private CensusTractMapper mapper;

    private CensusTract censusTract;

    private CensusTractDao censusTractDto;

    @Before
    public void setup() {
        this.censusTract = new CensusTract();
        censusTract.setGid(1);
        censusTract.setTract("1234");
    }

    @Test
    public void toDtoWorks() {
        CensusTractDto dto = this.mapper.toDto(censusTract);
        assertEquals(dto.getId(), this.censusTract.getGid());
        assertEquals(dto.getTract(), this.censusTract.getTract());
        assertEquals(dto.getGeometry(), this.censusTract.getPolygon());

    }

}