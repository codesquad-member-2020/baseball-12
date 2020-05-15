import { useState, useEffect } from 'react';

const usePostFetch = (callback, url = '', data = {}) => {
  const [loading, setLoading] = useState(true);
  const fetchInitialData = async () => {
    setLoading(true);
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
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

export default usePostFetch;
