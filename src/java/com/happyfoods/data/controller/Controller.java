package com.happyfoods.data.controller;

import java.util.Collection;

public interface Controller<T> {

	Collection<T> loadAll();
}
