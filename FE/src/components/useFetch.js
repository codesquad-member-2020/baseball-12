import { useState, useEffect } from 'react';

const useFetch = (callback, url) => {
  const [loading, setLoading] = useState(true);
  const fetchInitialData = async () => {
    setLoading(true);
    const response = await fetch(url);
    const fetchData = await response.json();
    if (!fetchData.status) return; //error
    callback(fetchData.data);
    setLoading(false);
  };

  useEffect(() => {
    fetchInitialData();
  }, []);

  return loading;
};

export default useFetch;
