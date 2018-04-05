package com.zheng.home.data;

import com.zheng.home.data.model.Pokemon;
import com.zheng.home.data.remote.PokemonApi;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataManager {

    private PokemonApi pokemonService;

    @Inject
    public DataManager(PokemonApi pokemonService) {
        this.pokemonService = pokemonService;
    }

    public Single<List<String>> getPokemonList(int limit) {
        return pokemonService
                .getPokemonList(limit)
                .toObservable()
                .flatMapIterable(namedResources -> namedResources.getResults())
                .map(namedResource -> namedResource.getName())
                .toList();
    }

    public Single<Pokemon> getPokemon(String name) {
        return pokemonService.getPokemon(name);
    }
}
