package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.infrastructure.repositoryImpl.mongo.MongoRecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RecipeRepositoryAdapterTest {

    @Mock
    private MongoRecipeRepository mongoRepository;

    private RecipeRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new RecipeRepositoryAdapter(mongoRepository);
    }

    @Test
    void shouldDelegateDeleteById() {
        String id = "recipe-1";
        adapter.deleteById(id);
        verify(mongoRepository).deleteById(id);
    }

    @Test
    void shouldDelegateFindById() {
        String id = "recipe-2";
        Recipe recipe = new Recipe();
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(mongoRepository.findById(id)).thenReturn(optionalRecipe);
        Optional<Recipe> result = adapter.findById(id);

        assertThat(result).isEqualTo(optionalRecipe);
        verify(mongoRepository).findById(id);
    }

    @Test
    void shouldDelegateFindByKeywordInIngredientsOrTitle() {
        String term = "chocolate";
        int limit = 5;

        List<Recipe> recipes = List.of(new Recipe(), new Recipe());
        when(mongoRepository.findByKeywordInIngredientsOrTitle(eq(term), any(Pageable.class))).thenReturn(recipes);

        List<Recipe> result = adapter.findByKeywordInIngredientsOrTitle(term, limit);

        assertThat(result).isEqualTo(recipes);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mongoRepository).findByKeywordInIngredientsOrTitle(eq(term), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(limit);
    }

    @Test
    void shouldDelegateFindAllWithSort() {
        Sort sort = Sort.by("title").ascending();
        List<Recipe> recipes = List.of(new Recipe());

        when(mongoRepository.findAll(sort)).thenReturn(recipes);
        List<Recipe> result = adapter.findAll(sort);

        assertThat(result).isEqualTo(recipes);
        verify(mongoRepository).findAll(sort);
    }

    @Test
    void shouldDelegateFindAllWithQuery() {
        int limit = 3;
        List<Recipe> recipes = List.of(new Recipe());

        when(mongoRepository.findAllWithQuery(any(Pageable.class))).thenReturn(recipes);
        List<Recipe> result = adapter.findAllWithQuery(limit);

        assertThat(result).isEqualTo(recipes);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mongoRepository).findAllWithQuery(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(limit);
    }

    @Test
    void shouldDelegateFindByKeywordInCategory() {
        String category = "desserts";
        int limit = 7;
        List<Recipe> recipes = List.of(new Recipe());

        when(mongoRepository.findByKeywordInCategory(eq(category), any(Pageable.class))).thenReturn(recipes);
        List<Recipe> result = adapter.findByKeywordInCategory(category, limit);

        assertThat(result).isEqualTo(recipes);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mongoRepository).findByKeywordInCategory(eq(category), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(limit);
    }

    @Test
    void shouldDelegateFindByCategoryAndKeyword() {
        String category = "desserts";
        String term = "cake";
        int limit = 4;
        List<Recipe> recipes = List.of(new Recipe());

        when(mongoRepository.findByCategoryAndKeyword(eq(category), eq(term), any(Pageable.class))).thenReturn(recipes);
        List<Recipe> result = adapter.findByCategoryAndKeyword(category, term, limit);

        assertThat(result).isEqualTo(recipes);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(mongoRepository).findByCategoryAndKeyword(eq(category), eq(term), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(limit);
    }

    @Test
    void shouldDelegateSave() {
        Recipe recipe = new Recipe();
        adapter.save(recipe);
        verify(mongoRepository).save(recipe);
    }
}
