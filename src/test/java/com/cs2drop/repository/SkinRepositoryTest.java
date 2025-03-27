package com.cs2drop.repository;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class SkinRepositoryTest {

    @Autowired
    private SkinRepository skinRepository;

    @Autowired
    private LootboxRepository lootboxRepository;

    private Lootbox testLootbox;

    @BeforeEach
    void setUp() {
        // Criar o Lootbox
        testLootbox = new Lootbox();
        testLootbox.setCaseName("Special Case");
        lootboxRepository.save(testLootbox);

        // Criar skins e associar ao Lootbox
        Skin skin1 = new Skin("AK-47 Redline", "Classified", 0.15, testLootbox);
        Skin skin2 = new Skin("Dragon Lore", "Legendary", 0.10, testLootbox);
        skinRepository.save(skin1);
        skinRepository.save(skin2);
    }

    @AfterEach
    void tearDown() {
        // Limpar as entidades após cada teste
        skinRepository.deleteAll();
        lootboxRepository.deleteAll();
    }

    @Test
    void testFindByLootboxId() {
        // Executar a consulta
        List<Skin> skins = skinRepository.findByLootbox_id(testLootbox.getCaseId());

        // Verificar o comportamento esperado
        assertNotNull(skins);
        assertEquals(2, skins.size());
        assertEquals("AK-47 Redline", skins.get(0).getSkinName());
        assertEquals("Dragon Lore", skins.get(1).getSkinName());
    }
}