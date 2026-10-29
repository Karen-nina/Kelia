import axios from 'axios';
import { useEffect, useState } from 'react';

export const useGetTypes = (page) => {
  const [data, setData] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

    const fetch = async () => {
        try {
            setLoading(true);
            
            const response = await axios.get("/data/mock_type.json");
            // se filter en cliente por trabajar con mock
            // TODO refactorizar para que se pase como api request al controllador.
            setTimeout(()=> {
              setData(response.data)
              setLoading(false);
            }, 200)        
        } catch (err) {
            setError(error);
            setLoading(false);
        }
    };

  useEffect(() => {
    fetch();
  }, [page]);

  return { data, error, loading };
};