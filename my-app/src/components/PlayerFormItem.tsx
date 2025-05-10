import React from 'react';
import {useFormik} from 'formik';
import * as Yup from 'yup';
import {useParams} from 'react-router-dom';
import {createPlayer} from '../services';

interface PlayerFormItemProps {
    onPlayerAdded: () => void; // Callback to refresh the player list or handle UI updates
}

const PlayerFormItem: React.FC<PlayerFormItemProps> = ({onPlayerAdded}) => {
    const {gameId} = useParams<{ gameId: string }>();

    const formik = useFormik({
        initialValues: {
            playerName: '',
        },
        validationSchema: Yup.object({
            playerName: Yup.string()
                .required('Player name is required.')
                .max(50, 'Player name must be 50 characters or less.'),
        }),
        onSubmit: async (values, {resetForm, setSubmitting}) => {
            try {
                if (!gameId) {
                    alert('Game ID is missing.');
                    return;
                }

                const playerRequest = {
                    name: values.playerName,
                    gameId,
                };

                await createPlayer(playerRequest);
                console.log('Player added successfully!');
                resetForm();
                onPlayerAdded();
            } catch (error) {
                console.error('Error adding player:', error);
                alert('An error occurred. Please try again.');
            } finally {
                setSubmitting(false);
            }
        },
    });

    return (
        <form onSubmit={formik.handleSubmit} className="player-form">
            <label htmlFor="playerName">Player Name:</label>
            <input
                id="playerName"
                name="playerName"
                type="text"
                value={formik.values.playerName}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.playerName && formik.errors.playerName ? (
                <div className="error">{formik.errors.playerName}</div>
            ) : null}
            <button type="submit" disabled={formik.isSubmitting}>
                {formik.isSubmitting ? 'Adding...' : 'Add Player'}
            </button>
        </form>
    );
};

export default PlayerFormItem;