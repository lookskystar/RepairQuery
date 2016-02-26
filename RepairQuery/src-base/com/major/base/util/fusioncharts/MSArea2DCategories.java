package com.major.base.util.fusioncharts;

import java.io.Serializable;
import java.util.List;

public class MSArea2DCategories implements Serializable {

	private static final long serialVersionUID = -6915929037278154102L;

	private List<MSArea2DCategory> category;

	public List<MSArea2DCategory> getCategory() {
		return category;
	}

	public void setCategory(List<MSArea2DCategory> category) {
		this.category = category;
	}

}
