import axios from "axios";
import { useEffect, useState } from "react";

export const useGetProducts = (searchTerms) => {
  const [data, setData] = useState({
    "filter-name": [],
    "filter-brand": [],
    "filter-type": [],
    products: [],
  });
  const [error, setError] = useState("");

  const [loading, setLoading] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const fetch = async (term = "") => {
    try {
      setLoading(true);
      const response = await axios.get(`${baseUrl}/productANMAT?term=${term}`);

      const products = response.data.products || [];

      const filterNames = [
        ...new Set(products.map((p) => p.salesName).filter(Boolean)),
      ];
      const filterBrands = [
        ...new Set(products.map((p) => p.brand).filter(Boolean)),
      ];
      const filterTypes = [
        ...new Set(products.map((p) => p.productType).filter(Boolean)),
      ];

      setData({
        "filter-name": filterNames,
        "filter-brand": filterBrands,
        "filter-type": filterTypes,
        products: products,
      });

      setLoading(false);
    } catch (err) {
      setError(err.message || "Unknown error");
      setLoading(false);
    }
  };

  useEffect(() => {
    fetch(searchTerms);
  }, [searchTerms]);

  return { data, error, loading };
};
