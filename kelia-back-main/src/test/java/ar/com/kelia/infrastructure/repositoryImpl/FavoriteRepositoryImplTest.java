package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaFavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteRepositoryImplTest {
    private JpaFavoriteRepository jpaRepository;
    private FavoriteRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(JpaFavoriteRepository.class);
        repository = new FavoriteRepositoryImpl();
        repository.repository = jpaRepository;
    }

    @Test
    void testFindByUid() {
        Favorite fav = new Favorite();
        when(jpaRepository.findByUid("user1")).thenReturn(Optional.of(fav));
        Optional<Favorite> result = repository.findByUid("user1");
        assertTrue(result.isPresent());
        assertEquals(fav, result.get());
    }

    @Test
    void testSaveNewFavorite() {
        String recipeId = "r1";
        String uid = "user1";
        when(jpaRepository.findByUid(uid)).thenReturn(Optional.empty());
        Favorite saved = new Favorite(null, new ArrayList<>(List.of(recipeId)), uid);
        when(jpaRepository.save(any(Favorite.class))).thenReturn(saved);
        Favorite result = repository.save(recipeId, uid);
        assertEquals(saved, result);
        verify(jpaRepository).save(any(Favorite.class));
    }

    @Test
    void testSaveExistingFavoriteAddsRecipe() {
        String recipeId = "r2";
        String uid = "user2";
        Favorite fav = new Favorite(null, new ArrayList<>(List.of("r1")), uid);
        when(jpaRepository.findByUid(uid)).thenReturn(Optional.of(fav));
        when(jpaRepository.save(any(Favorite.class))).thenReturn(fav);
        Favorite result = repository.save(recipeId, uid);
        assertEquals(fav, result);
        assertTrue(fav.getRecipes().contains(recipeId));
        verify(jpaRepository).save(fav);
    }

    @Test
    void testSaveExistingFavoriteNoDuplicate() {
        String recipeId = "r1";
        String uid = "user2";
        Favorite fav = new Favorite(null, new ArrayList<>(List.of(recipeId)), uid);
        when(jpaRepository.findByUid(uid)).thenReturn(Optional.of(fav));
        when(jpaRepository.save(any(Favorite.class))).thenReturn(fav);
        Favorite result = repository.save(recipeId, uid);
        assertEquals(fav, result);
        assertEquals(1, fav.getRecipes().size());
        verify(jpaRepository).save(fav);
    }

    @Test
    void testGetFavoriteRecipeFromUid() {
        Favorite fav = new Favorite();
        when(jpaRepository.findByUid("user3")).thenReturn(Optional.of(fav));
        Optional<Favorite> result = repository.getFavoriteRecipeFromUid("user3");
        assertTrue(result.isPresent());
        assertEquals(fav, result.get());
    }
}