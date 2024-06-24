import React, { useEffect, useState } from 'react'

const List = () => {
    const [products, setProducts] = useState([]);
    const zoomSizeImage = (e) => {
        if(e.target.style.width === '50px'){
            
            e.target.style.width = '100px';
        } else {
            e.target.style.width = '50px';
        }
    }
    useEffect(() => {
        fetch('https://fakestoreapi.com/products')
            .then(res => res.json())
            .then(json => setProducts(json))
    }, []);

    return (
        <div className='container mt-3'>
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Image</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map(product => (
                        <tr key={product.id}>
                            <td
                                style={{ textAlign:"left"}}
                            >{product.title}</td>
                            <td>{product.price}</td>
                            <td><img
                            onClick={(e) => zoomSizeImage(e)}
                             src={product.image} alt={product.title} style={{ width: '50px' }} /></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default List
