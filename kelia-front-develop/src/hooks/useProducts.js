import axios from 'axios';
import { useEffect, useState } from 'react';


export const useGetProducts = (term) => {
  const [data, setData] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const fetch = async () => {
        try {
            setLoading(true);
            setData([])
            const response = await axios.get(`${baseUrl}/product?term=${term}&q=20`)
            setData(response.data)
            setLoading(false);      
        } catch (err) {
            setData([])
            setError(error);
            setLoading(false);
        }
    };

  useEffect(() => {
    fetch();
  }, [term]);

  return { data , error, loading };
};