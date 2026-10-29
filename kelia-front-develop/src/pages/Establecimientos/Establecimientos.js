import React, { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { useJsApiLoader } from '@react-google-maps/api';
import PlacesMap from "../../components/Places/PlacesMap";
import PlaceDetail from '../../components/Places/PlaceDetail';
import SearchBar from '../../components/Inicio/SearchBar';
import axios from 'axios';
import './Establecimientos.css';
import Spinner from 'react-bootstrap/Spinner';
import PlaceComment from '../../components/Places/PlaceComment';

const libraries = ['places'];

const Establecimientos = () => {
  const [selectedPlace, setSelectedPlace] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [results, setResults] = useState([]);
  const [apiData, setApiData] = useState(null);
  const [apiError, setApiError] = useState(false);
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const [lugares, setLugares] = useState([]);
  const [loadingLugares, setLoadingLugares] = useState(true);
  const [errorLugares, setErrorLugares] = useState(false);
  const { id } = useParams();

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries,
  });

  const handleSearchChange = (e) => {
    const value = e.target.value;
    setSearchTerm(value);

    const filtered = lugares.filter(lugar =>
      lugar.name.toLowerCase().includes(value.toLowerCase())
    );
    setResults(filtered);
  };

  const handleSelect = (lugar) => {
    setSelectedPlace(lugar);
    setSearchTerm('');
    setResults([]);
    setApiData(null);
    setApiError(false);
  };

  useEffect(() => {
    const fetchLugares = async () => {
      try {
        const response = await axios.get(`${baseUrl}/places`);
        if (response.data && Array.isArray(response.data.places)) {
          setLugares(response.data.places);
          setErrorLugares(false);
        } else {
          setLugares([]);
          setErrorLugares(true);
        }
      } catch (error) {
        console.error('Error al obtener lugares:', error);
        setErrorLugares(true);
        setLugares([]);
      } finally {
        setLoadingLugares(false);
      }
    };

    fetchLugares();
  }, []);

  useEffect(() => {
    if (id && lugares.length > 0) {
      const found = lugares.find(lugar => lugar.place_id === id || lugar.id === id);
      if (found) {
        setSelectedPlace(found);
      }
    }
  }, [id, lugares]);

  useEffect(() => {
    if (selectedPlace) {
      const fetchData = async () => {
        try {
          const response = await axios.get(`${baseUrl}/places/${selectedPlace.place_id}`);
          if (response.data && Object.keys(response.data).length > 0) {
            setApiData(response.data);
            setApiError(false);
          } else {
            setApiData(null);
            setApiError(true);
          }
        } catch (error) {
          console.error('Error al obtener datos desde Google Maps:', error);
          setApiData(null);
          setApiError(true);
        }
      };

      fetchData();
    }
  }, [selectedPlace]);

  const buscadorRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (buscadorRef.current && !buscadorRef.current.contains(event.target)) {
        setDropdownVisible(false);
        setSearchTerm('');
        setResults([]);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  if (!isLoaded || loadingLugares) {
    return (
      <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '200px' }}>
        <p className="carga-mapa mb-3">Cargando mapa y lugares...</p>
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Cargando...</span>
        </Spinner>
      </div>
    );
  }

  if (errorLugares) {
    return (
      <div className="text-center mt-5">
        <p className="text-danger">Error al cargar los establecimientos. Intenta más tarde.</p>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="establecimientos-wrapper">
        <div ref={buscadorRef} className="dropdown buscador position-relative w-100">
          
          <SearchBar
            value={searchTerm}
            placeholder="Buscar establecimiento..."
            onChange={(e) => {
              handleSearchChange(e);
              setDropdownVisible(true);
            }}
          />

          {dropdownVisible && results.length > 0 && (
            <ul className="dropdown-menu show w-100" style={{ maxHeight: '200px', overflowY: 'auto' }}>
              {results.map((lugar, idx) => (
                <li key={idx}>
                  <button
                    className="dropdown-item"
                    type="button"
                    onClick={() => {
                      handleSelect(lugar);
                      setDropdownVisible(false);
                    }}
                  >
                    {lugar.name}
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="establecimientos-grid">
          <div className="map-column">
            <PlacesMap
              lugares={lugares}
              onSelectPlace={setSelectedPlace}
              selectedPlace={selectedPlace}
            />
          </div>
          <div className="detail-column">
            {selectedPlace && (
              <>
                <PlaceDetail
                  lugar={selectedPlace}
                  apiError={apiError}
                  apiData={apiData}
                  onClose={() => setSelectedPlace(null)}
                />
                <PlaceComment 
                  placeId={selectedPlace.id}
                />
              </>
            )}
          </div>
        </div>
      </div>
    </div> 
  );
};

export default Establecimientos;