import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function UrlForm() {
  const [fullUrl, setFullUrl] = useState('');
  const [customAlias, setCustomAlias] = useState('');
  const [urls, setUrls] = useState([]);

  useEffect(() => { fetchUrls(); }, []);

  const fetchUrls = async () => {
    try {
      await axios.get('http://localhost:8080/urls').then(res => setUrls(res.data));
    } catch(e) {
      console.log(e);
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/shorten', { fullUrl, customAlias });
      setFullUrl('');
      setCustomAlias('');
      fetchUrls();
    } catch (err) {
      if (err.response) {
        alert("Error: " + err.response.data.message);
      } else {
        alert("Unexpected error: " + err.message);
      }
    }
  }

  return (
    <>
      <form onSubmit={handleSubmit}>
        <input value={fullUrl} onChange={e => setFullUrl(e.target.value)} placeholder="Full URL" required />
        <input value={customAlias} onChange={e => setCustomAlias(e.target.value)} placeholder="Custom alias (optional)" />
        <button type="submit">Shorten</button>
      </form>

      <h2>All Shortened URLs</h2>
      <ul>
        {urls.map(url => (
          <li key={url.alias}>
            {url.shortUrl} → {url.fullUrl}
          </li>
        ))}
      </ul>
    </>
  );
}
